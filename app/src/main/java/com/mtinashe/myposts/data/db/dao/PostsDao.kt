package com.mtinashe.myposts.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtinashe.myposts.data.entities.Author
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.Post
import com.mtinashe.myposts.data.entities.joins.JoinPostData

@Dao
interface PostsDao {

    @Query("SELECT posts.id, posts.title, posts.body, authors.name, authors.phone, authors.user_name FROM posts INNER JOIN authors on posts.authorId == authors.id")
    fun getAllPostsWithAuthors(): LiveData<List<JoinPostData>>

    @Query("SELECT posts.id, posts.title, posts.body, authors.name, authors.phone, authors.user_name FROM posts INNER JOIN authors on posts.authorId == authors.id WHERE posts.id = :postId")
    fun getPostsWithAuthorsByPostId(postId: Int): LiveData<JoinPostData>

    @Query("SELECT * FROM comments WHERE post_id = :postId ")
    fun getAllCommentsByPostId(postId: Int): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(allPosts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllComments(allComments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAuthors(allAuthors: List<Author>)
}
