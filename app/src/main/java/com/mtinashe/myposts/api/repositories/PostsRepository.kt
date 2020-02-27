package com.mtinashe.myposts.api.repositories

import com.mtinashe.myposts.api.retrofit.ApiFactory

class PostsRepository {
    val client = ApiFactory.apiService

    suspend fun getPosts() = client.getAllPosts()
}