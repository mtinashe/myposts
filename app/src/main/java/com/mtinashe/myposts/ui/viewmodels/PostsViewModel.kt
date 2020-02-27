package com.mtinashe.myposts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mtinashe.myposts.api.repositories.PostsRepository
import kotlinx.coroutines.Dispatchers

class PostsViewModel : ViewModel() {
    private val postsRepository = PostsRepository()

    val allPosts = liveData(Dispatchers.IO) {
        val posts = postsRepository.getPosts()

        emit(posts)
    }
}