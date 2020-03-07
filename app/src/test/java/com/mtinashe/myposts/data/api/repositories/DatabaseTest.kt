package com.mtinashe.myposts.data.api.repositories

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mtinashe.myposts.data.db.AppDatabase
import com.mtinashe.myposts.data.db.dao.PostsDao
import com.mtinashe.myposts.data.entities.Author
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.test_utils.IsMainExecutorRule
import java.io.IOException
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = IsMainExecutorRule()

    private lateinit var postsDao: PostsDao
    private lateinit var db: AppDatabase

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
        postsDao = db.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertPostsAndReadInserted() = testScope.runBlockingTest {
        val post = Post(1, "Hello World", "Hello World", 1)
        val author = Author("tmakuti@i.com", 1, "Tinashe", "12344", "Nashe", "www.mtinashe.xyz")
        postsDao.insertAllPosts(listOf(post))
        postsDao.insertAllAuthors(listOf(author))
        val byPostId = postsDao.getPostsWithAuthorsByPostId(1)
        assertTrue(byPostId.authorName == "Tinashe")
    }
}
