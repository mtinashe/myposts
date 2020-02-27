package com.mtinashe.myposts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mtinashe.myposts.data.api.repositories.PostsRepository
import kotlinx.coroutines.Dispatchers

class PostsViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    val allPosts = liveData(Dispatchers.IO) {
        val posts = postsRepository.getPosts()
        emit(posts)
    }

    val allCommentsByPost = liveData(Dispatchers.IO) {
        val comments = postsRepository.getCommentsByPost(4)
        emit(comments)
    }

    val authorById = liveData (Dispatchers.IO) {
        val author = postsRepository.getAuthorById(4)
        emit(author)
    }
}