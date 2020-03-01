package com.mtinashe.myposts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository

import kotlinx.coroutines.Dispatchers

class PostsViewModel(private val postsRepository: SuspendingPostRepository) : ViewModel() {

    val allPosts = liveData(Dispatchers.IO) {
        val posts = postsRepository.getPostsFromDb()
        emit(posts)
    }
}