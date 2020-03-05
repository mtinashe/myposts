package com.mtinashe.myposts.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.data.entities.joins.JoinPostData
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

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repository = mock(SuspendingPostRepository::class.java)
        viewModel = PostsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test all posts from repository`(){
        val postObserver = mock<Observer<List<JoinPostData>>>()
        viewModel.allPosts.observeForever(postObserver)
        runBlocking {
            verify(repository).getPostsFromDb()
        }
    }

    @Test
    fun `test get all comments by post from repository`() {
        val commentsObserver = mock<Observer<List<Comment>>>()
        viewModel.setPostId(1)
        viewModel.comments.observeForever(commentsObserver)
        runBlocking {
            verify(repository).getCommentsByPostFromDb(1)
        }
    }

    @Test
    fun `test get single post joined with author from repository`(){
        val singlePostObserver = mock<Observer<JoinPostData>>()
        viewModel.setPostId(1)
        viewModel.post.observeForever(singlePostObserver)
        runBlocking {
            verify(repository).getPostById(1)
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
}