package com.mtinashe.myposts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @ColumnInfo(name = "email") val email: String = "",
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "user_name") val userName: String = "",
    @ColumnInfo(name = "website") val website: String = ""
)
