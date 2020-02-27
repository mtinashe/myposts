package com.mtinashe.myposts.api.retrofit

import com.mtinashe.myposts.api.entities.Author
import com.mtinashe.myposts.api.entities.Comment
import com.mtinashe.myposts.api.entities.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getAllPosts() : List<Post>

    @GET("comments?")
    suspend fun getAllCommentsByPostId(@Query("postId") postId : String) : List<Comment>

    @GET("users/{id}")
    suspend fun getAllUsersById(@Path(value = "id") userId : String) : Author

}