package com.mtinashe.myposts.data.api.retrofit

import com.mtinashe.myposts.data.entities.Author
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getAllPosts() : List<Post>

    @GET("comments?")
    suspend fun getAllComments() : List<Comment>

    @GET("users/")
    suspend fun getAllUsers() : List<Author>

}