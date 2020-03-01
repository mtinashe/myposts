package com.mtinashe.myposts.data.api.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtinashe.myposts.data.api.retrofit.ApiService
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito

class PostsRepositoryTest {

    lateinit var client : ApiService
    val expectedData = Post("tinashe", 6, "testing", 1)


    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        client = mock()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test if repository is getting posts from client`() = runBlockingTest {
        `stub get all posts`()
        val actual = client.getAllPosts()

        assertTrue(actual.isNotEmpty())
    }

    @Test
    fun getCommentsByPost() {

    }

    @Test
    fun getAuthorById() {
    }

    suspend fun `stub get all posts`(){
        Mockito.`when`(client.getAllPosts()).thenReturn(listOf(expectedData))
    }
}