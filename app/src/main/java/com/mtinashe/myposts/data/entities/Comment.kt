package com.mtinashe.myposts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "body") val body: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "post_id")val postId: Int = 0
)
