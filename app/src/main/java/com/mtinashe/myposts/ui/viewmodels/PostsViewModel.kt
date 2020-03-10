package com.mtinashe.myposts.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mtinashe.myposts.coroutines.DefaultDispatcherProvider
import com.mtinashe.myposts.coroutines.DispatcherProvider
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository

class PostsViewModel(
    private val postsRepository: SuspendingPostRepository,
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private var postId: MutableLiveData<Int> = MutableLiveData()
    val allPosts = postsRepository.getPostsFromDb()
    val post = Transformations.switchMap(postId) {
        postsRepository.getPostById(it)
    }

    val comments = Transformations.switchMap(postId) {
        postsRepository.getCommentsByPostFromDb(it)
    }

    fun setPostId(id: Int) {
        postId.value = id
    }
}
