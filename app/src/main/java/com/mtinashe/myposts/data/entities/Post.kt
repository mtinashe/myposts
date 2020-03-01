package com.mtinashe.myposts.data.entities

import com.google.gson.annotations.SerializedName

data class Post(
    var body: String = "",
    var id: Int = 0,
    var title: String = "",
    @SerializedName("userId") var authorId: Int = 0
)