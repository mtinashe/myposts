package com.mtinashe.myposts.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class PostsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = InstantTaskExecutorRule()

    private lateinit var repository: SuspendingPostRepository
    private lateinit var viewModel: PostsViewModel

    private val expectedData = Post(0,0,"tinashe", "tinashe", 1)
    private val expectedComment = Comment(0,0,"Tinasge", "tmakuti@icloud.com", "2", 6)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repository = mock(SuspendingPostRepository::class.java)
        viewModel = PostsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAllPosts(){
        val postObserver = mock<Observer<List<Post>>>()
        viewModel.allPosts.observeForever(postObserver)
        runBlocking {
            verify(repository).getPosts()
        }
    }

    @Test
    fun getAllCommentsByPost() {
        val commentsObserver = mock<Observer<List<Comment>>>()
        viewModel.allCommentsByPost.observeForever(commentsObserver)
        runBlocking {
            `when`(repository.getCommentsByPost()).thenReturn(listOf(expectedComment))
            verify(repository).getCommentsByPost()
        }
    }

    @Test
    fun getAuthorById() {
    }


    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
}