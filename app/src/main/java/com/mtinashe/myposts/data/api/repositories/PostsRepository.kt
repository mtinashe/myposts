package com.mtinashe.myposts.data.api.repositories

import com.mtinashe.myposts.data.api.retrofit.ApiFactory
import com.mtinashe.myposts.data.api.retrofit.ApiService
import com.mtinashe.myposts.data.entities.Author
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post

open class PostsRepository : SuspendingPostRepository {
    private val client: ApiService = ApiFactory.apiService

    override suspend fun getPosts() = client.getAllPosts()
    override suspend fun getCommentsByPost(postId : Int) = client.getAllCommentsByPostId(postId)
    override suspend fun getAuthorById(userId : Int) = client.getAllUsersById(userId)
}

interface SuspendingPostRepository{
    suspend fun getPosts() : List<Post>
    suspend fun getCommentsByPost(postId: Int = 7) : List<Comment>
    suspend fun getAuthorById(userId: Int) : Author
}