package com.mtinashe.myposts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository

import kotlinx.coroutines.Dispatchers

class PostsViewModel(private val postsRepository: SuspendingPostRepository) : ViewModel() {

    private var postId : Int = 0

    val allPosts = liveData(Dispatchers.IO) {
        val posts = postsRepository.getPostsFromDb()
        emit(posts)
    }

    val post = liveData(Dispatchers.IO){
        val post = postsRepository.getPostById(postId)
        emit(post)
    }

    val comments = liveData ( Dispatchers.IO ){
        val comments = postsRepository.getCommentsByPostFromDb(postId)
        emit(comments)
    }

    fun setPostId(id : Int){
        postId = id
    }
}