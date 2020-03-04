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

    private val expectedData = Post(1,"Hello World","Hello World", 1)
    private val expectedComment = Comment(1,"Hello world", "tmakuti@icloud.com","Tinashe", 1)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repository = mock(SuspendingPostRepository::class.java)
        viewModel = PostsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAllPostsFromApi(){
        val postObserver = mock<Observer<List<Post>>>()
        viewModel.allPosts.observeForever(postObserver)
        runBlocking {
            verify(repository).getPostsFromDb()
        }
    }

    @Test
    fun getAllCommentsByPostFromApi() {
        val commentsObserver = mock<Observer<List<Comment>>>()
        viewModel.comments.observeForever(commentsObserver)
        runBlocking {
            `when`(repository.get()).thenReturn(listOf(expectedComment))
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