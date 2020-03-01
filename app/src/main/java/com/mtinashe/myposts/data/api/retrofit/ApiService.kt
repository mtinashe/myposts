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
    suspend fun getAllCommentsByPostId(@Query("postId") postId : Int) : List<Comment>

    @GET("users/{id}")
    suspend fun getAllUsersById(@Path(value = "id") userId : Int) : Author

}