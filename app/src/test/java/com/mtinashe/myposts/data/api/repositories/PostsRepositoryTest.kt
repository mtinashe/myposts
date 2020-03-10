package com.mtinashe.myposts.data.api.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtinashe.myposts.data.api.retrofit.ApiService
import com.mtinashe.myposts.data.db.dao.PostsDao
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import com.mtinashe.myposts.test_utils.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException

class PostsRepositoryTest {

    private lateinit var client: ApiService
    private lateinit var dao: PostsDao
    private lateinit var httpException: HttpException
    private val expectedPostDataClient = Post(0, "tinashe", "tinashe", 1)
    private val expectedPostDataDao = JoinPostData(0, "tinashe", "tinashe", "Tinashe")
    private val expectedComment = Comment(0, "Tinasge", "tmakuti@icloud.com", "2", 6)

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        client = mock()
        dao = mock()
        httpException = mock()
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
    fun `test if repository is getting comments from client`() = runBlockingTest {
        `stub get all comments for a post`()
        val returnedComments = client.getAllComments()
        assertTrue(returnedComments.isNotEmpty())
    }

    private suspend fun `stub get all posts`() {
        Mockito.`when`(client.getAllPosts()).thenReturn(listOf(expectedPostDataClient))
    }

    private suspend fun `stub get all comments for a post`() {
        Mockito.`when`(client.getAllComments()).thenReturn(listOf(expectedComment))
    }
}
