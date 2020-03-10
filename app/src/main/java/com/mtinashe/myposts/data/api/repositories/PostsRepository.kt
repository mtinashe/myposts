package com.mtinashe.myposts.data.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mtinashe.myposts.data.api.Resource
import com.mtinashe.myposts.data.api.ResponseHandler
import com.mtinashe.myposts.data.api.Status
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
import timber.log.Timber

open class PostsRepository(private val postsDao: PostsDao, private val responseHandler: ResponseHandler) : SuspendingPostRepository {
    private val client: ApiService = ApiFactory.apiService

    val errorMessage = MutableLiveData<String>()

    init {
        this.sync()
    }

    // API WORK
    override suspend fun getPostsFromApi(): Resource<List<Post>> {
       return try {
            responseHandler.handleSuccess(client.getAllPosts())
        } catch (exception : Exception){
            responseHandler.handleException(exception)
        }
    }

    override suspend fun getCommentsFromApi() : Resource<List<Comment>> {
        return try {
            responseHandler.handleSuccess(client.getAllComments())
        } catch (exception : Exception){
            responseHandler.handleException(exception)
        }
    }

    override suspend fun getAuthorsFromApi() : Resource<List<Author>>{
        return try {
            responseHandler.handleSuccess(client.getAllUsers())
        } catch (exception : Exception){
            responseHandler.handleException(exception)
        }
    }

    // local db work
    override suspend fun persistAllPosts(updatedPost: List<Post>) = postsDao.insertAllPosts(updatedPost)
    override suspend fun persistAllComments(updatedComments: List<Comment>) = postsDao.insertAllComments(updatedComments)
    override suspend fun persistAllAuthors(updatedAuthors: List<Author>) = postsDao.insertAllAuthors(updatedAuthors)

    // avail data to view model
    override fun getCommentsByPostFromDb(postId: Int) = postsDao.getAllCommentsByPostId(postId)
    override fun getPostsFromDb() = postsDao.getAllPostsWithAuthors()
    override fun getPostById(postId: Int): LiveData<JoinPostData> {
        return postsDao.getPostsWithAuthorsByPostId(postId)
    }

    // sync
    override fun sync() {
        val allPosts = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(this@PostsRepository.getPostsFromApi())
        }

        val allComments = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(this@PostsRepository.getCommentsFromApi())
        }

        val allAuthors = liveData(Dispatchers.IO) {
            val author = this@PostsRepository.getAuthorsFromApi()
            emit(Resource.loading(null))
            emit(this@PostsRepository.getAuthorsFromApi())
        }

        allPosts.observeForever { postsInResource ->
            GlobalScope.launch(Dispatchers.IO) {
                when(postsInResource.status){
                    Status.SUCCESS -> postsInResource.data?.let { persistAllPosts(it) }
                    Status.ERROR -> setErrorLiveData(postsInResource.message)
                    Status.LOADING -> Timber.d("Post Loading")
                }
            }
        }

        allComments.observeForever { commentsInResource ->
            GlobalScope.launch(Dispatchers.IO) {
                when(commentsInResource.status){
                    Status.SUCCESS -> commentsInResource.data?.let { persistAllComments(it) }
                    Status.ERROR -> setErrorLiveData(commentsInResource.message)
                    Status.LOADING -> Timber.d("Comments Loading")
                }
            }
        }

        allAuthors.observeForever { authorsInResource ->
            GlobalScope.launch(Dispatchers.IO) {
                when(authorsInResource.status){
                    Status.SUCCESS -> authorsInResource.data?.let { persistAllAuthors(it) }
                    Status.ERROR -> setErrorLiveData(authorsInResource.message)
                    Status.LOADING -> Timber.d("Author Loading")
                }
            }
        }
    }

    private fun setErrorLiveData(errorMessage : String?){
        Timber.e(errorMessage)
    }
}

interface SuspendingPostRepository {
    suspend fun getPostsFromApi(): Resource<List<Post>>
    suspend fun getCommentsFromApi(): Resource<List<Comment>>
    suspend fun getAuthorsFromApi(): Resource<List<Author>>

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
