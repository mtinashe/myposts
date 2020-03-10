package com.mtinashe.myposts.data.entities.joins

import androidx.room.ColumnInfo

data class JoinPostData(
    @ColumnInfo(name = "id") val postId: Int = 0,
    @ColumnInfo(name = "title") val postTitle: String = "",
    @ColumnInfo(name = "body") val postBody: String = "",
    @ColumnInfo(name = "name") val authorName: String = "",
    @ColumnInfo(name = "phone") val authorPhone: String = "",
    @ColumnInfo(name = "user_name") val userName: String = ""
)
