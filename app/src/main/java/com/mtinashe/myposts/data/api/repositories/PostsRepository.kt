package com.mtinashe.myposts.data.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mtinashe.myposts.data.api.retrofit.ApiFactory
import com.mtinashe.myposts.data.api.retrofit.ApiService
import com.mtinashe.myposts.data.db.dao.PostsDao
import com.mtinashe.myposts.data.entities.Author
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class PostsRepository (private val postsDao : PostsDao) : SuspendingPostRepository {
    private val client: ApiService = ApiFactory.apiService

    private var allPostsFromApi : LiveData<List<Post>>? = null
    private var allCommentsFromApi : LiveData<List<Comment>>? = null
    private var allAuthorsFromApi : LiveData<List<Author>>? = null

    init {
          allPostsFromApi = liveData(Dispatchers.IO) {
            val posts = this@PostsRepository.getPostsFromApi()
            emit(posts)
          }

        allCommentsFromApi = liveData(Dispatchers.IO) {
            val comments = this@PostsRepository.getCommentsFromApi()
            emit(comments)
        }

        allAuthorsFromApi = liveData (Dispatchers.IO) {
            val author = this@PostsRepository.getAuthorsFromApi()
            emit(author)
        }

        allPostsFromApi?.observeForever{ posts ->
            GlobalScope.launch (Dispatchers.IO) {
                persistAllPosts(posts)
            }
        }

        allCommentsFromApi?.observeForever {comments ->
            GlobalScope.launch (Dispatchers.IO) {
                persistAllComments(comments)
            }
        }

        allAuthorsFromApi?.observeForever{authors ->
            GlobalScope.launch (Dispatchers.IO) {
                persistAllAuthors(authors)
            }
        }
    }

    //API WORK
    override suspend fun getPostsFromApi() = client.getAllPosts()
    override suspend fun getCommentsFromApi() = client.getAllComments()
    override suspend fun getAuthorsFromApi() = client.getAllUsers()

    //local db work
    override suspend fun persistAllPosts(updatedPost: List<Post>) = postsDao.insertAllPosts(updatedPost)
    override suspend fun persistAllComments(updatedComments: List<Comment>) = postsDao.insertAllComments(updatedComments)
    override suspend fun persistAllAuthors(updatedAuthors: List<Author>)= postsDao.insertAllAuthors(updatedAuthors)
    override suspend fun getCommentsByPostFromDb(postId : Int) = postsDao.getAllCommentsByPostId(postId)
    override suspend fun getPostsFromDb() = postsDao.getAllPostsWithAuthors()
    override suspend fun getPostById(postId: Int) = postsDao.getPostsWithAuthorsByPostId(postId)

}

interface SuspendingPostRepository{
    suspend fun getPostsFromApi() : List<Post>
    suspend fun getCommentsFromApi() : List<Comment>
    suspend fun getAuthorsFromApi() : List<Author>

    //persist into local storage
    suspend fun persistAllPosts(updatedPost: List<Post>)
    suspend fun persistAllComments(updatedComments: List<Comment>)
    suspend fun persistAllAuthors(updatedAuthors: List<Author>)

    //get data from local storage
    suspend fun getPostsFromDb() : List<JoinPostData>
    suspend fun getCommentsByPostFromDb(postId: Int) : List<Comment>
    suspend fun getPostById(postId: Int) : JoinPostData
}