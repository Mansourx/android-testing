package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Ahmad Mansour on 29/08/2021
 * NAMSHI General Trading,
 * Dubai, UAE.
 */


class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")

    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }


    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource, tasksLocalDataSource,
            Dispatchers.Unconfined
        )
    }

    // We use runBlockingTest function when we call suspend function. to run the code Synchronously
    // and making the code to run as non-coroutine for testing purpose.
    // runBlockingTest from this Experimental Library
    //'testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"'
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_RequestAllTasksFromRemoteDataSource() = runBlockingTest {
        // When Tasks requested from the datasource
        val tasks = tasksRepository.getTasks(true) as Result.Success
        // Then tasks are loaded from remote datasource.
        assertThat(tasks.data, IsEqual(remoteTasks))
    }

}