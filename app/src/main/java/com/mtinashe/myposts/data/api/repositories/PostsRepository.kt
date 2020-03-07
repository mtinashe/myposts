package com.mtinashe.myposts.data.api.repositories

import android.util.Log
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

open class PostsRepository(private val postsDao: PostsDao) : SuspendingPostRepository {
    private val client: ApiService = ApiFactory.apiService

    private var allPostsFromApi: LiveData<List<Post>>? = null
    private var allCommentsFromApi: LiveData<List<Comment>>? = null
    private var allAuthorsFromApi: LiveData<List<Author>>? = null

    init {
        this.sync()
    }

    // API WORK
    override suspend fun getPostsFromApi() = client.getAllPosts()
    override suspend fun getCommentsFromApi() = client.getAllComments()
    override suspend fun getAuthorsFromApi() = client.getAllUsers()

    // local db work
    override suspend fun persistAllPosts(updatedPost: List<Post>) = postsDao.insertAllPosts(updatedPost)
    override suspend fun persistAllComments(updatedComments: List<Comment>) = postsDao.insertAllComments(updatedComments)
    override suspend fun persistAllAuthors(updatedAuthors: List<Author>) = postsDao.insertAllAuthors(updatedAuthors)

    //avail data to view model
    override fun getCommentsByPostFromDb(postId: Int) = postsDao.getAllCommentsByPostId(postId)
    override fun getPostsFromDb() = postsDao.getAllPostsWithAuthors()
    override fun getPostById(postId: Int): LiveData<JoinPostData> {
        Log.d("ArticleFragment", postId.toString())
        return postsDao.getPostsWithAuthorsByPostId(postId)
    }

    // sync
    override fun sync() {
        allPostsFromApi = liveData(Dispatchers.IO) {
            val posts = this@PostsRepository.getPostsFromApi()
            emit(posts)
        }

        allCommentsFromApi = liveData(Dispatchers.IO) {
            val comments = this@PostsRepository.getCommentsFromApi()
            emit(comments)
        }

        allAuthorsFromApi = liveData(Dispatchers.IO) {
            val author = this@PostsRepository.getAuthorsFromApi()
            emit(author)
        }

        allPostsFromApi?.observeForever { posts ->
            GlobalScope.launch(Dispatchers.IO) {
                persistAllPosts(posts)
            }
        }

        allCommentsFromApi?.observeForever { comments ->
            GlobalScope.launch(Dispatchers.IO) {
                persistAllComments(comments)
            }
        }

        allAuthorsFromApi?.observeForever { authors ->
            GlobalScope.launch(Dispatchers.IO) {
                persistAllAuthors(authors)
            }
        }
    }
}

interface SuspendingPostRepository {
    suspend fun getPostsFromApi(): List<Post>
    suspend fun getCommentsFromApi(): List<Comment>
    suspend fun getAuthorsFromApi(): List<Author>

    // persist into local storage
    suspend fun persistAllPosts(updatedPost: List<Post>)

    suspend fun persistAllComments(updatedComments: List<Comment>)
    suspend fun persistAllAuthors(updatedAuthors: List<Author>)

    // get data from local storage
    fun getPostsFromDb(): LiveData<List<JoinPostData>>
    fun getCommentsByPostFromDb(postId: Int): LiveData<List<Comment>>
    fun getPostById(postId: Int): LiveData<JoinPostData>

    // sync
    fun sync()
}
