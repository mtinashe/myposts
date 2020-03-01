package com.mtinashe.myposts.data.api.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtinashe.myposts.data.api.retrofit.ApiService
import com.mtinashe.myposts.data.entities.Comment
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
    private val expectedData = Post(0,"tinashe", "tinashe", 1)
    private val expectedComment = Comment(0,"Tinasge", "tmakuti@icloud.com", "2", 6)


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
        val returnedPosts = client.getAllPosts()
        assertTrue(returnedPosts.isNotEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test if repository is getting comments from client based on post id`() = runBlockingTest{
        `stub get all comments for a post`()
        val returnedComments = client.getAllComments()
        assertTrue(returnedComments.isNotEmpty())
    }

    @Test
    fun getAuthorById() {

    }

    private suspend fun `stub get all posts`(){
        Mockito.`when`(client.getAllPosts()).thenReturn(listOf(expectedData))
    }

    private suspend fun `stub get all comments for a post`(){
        Mockito.`when`(client.getAllComments()).thenReturn(listOf(expectedComment))
    }
}