package com.mtinashe.myposts.data.api.repositories

import com.mtinashe.myposts.data.api.retrofit.ApiFactory

class PostsRepository {
    val client = ApiFactory.apiService

    suspend fun getPosts() = client.getAllPosts()
    suspend fun getCommentsByPost(postId : Int) = client.getAllCommentsByPostId(postId)
    suspend fun getAuthorById(userId : Int) = client.getAllUsersById(userId)
}