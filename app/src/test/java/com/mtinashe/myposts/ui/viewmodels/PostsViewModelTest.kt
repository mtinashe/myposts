package com.mtinashe.myposts.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.test_utils.CoroutineTestRule
import com.mtinashe.myposts.test_utils.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class PostsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: SuspendingPostRepository
    private lateinit var viewModel: PostsViewModel
    private var commentsObserver: Observer<List<Comment>> = mock()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repository = mock(SuspendingPostRepository::class.java)
        viewModel = PostsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get all comments by post from repository`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.setPostId(1)
        viewModel.comments.observeForever(commentsObserver)
        verify(repository).getCommentsByPostFromDb(1)
    }
}
