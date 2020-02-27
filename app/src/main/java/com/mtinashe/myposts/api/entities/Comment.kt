package com.mtinashe.myposts.api.entities


import com.google.gson.annotations.SerializedName

data class Comment(
    var body: String = "",
    var email: String = "",
    var id: Int = 0,
    var name: String = "",
    var postId: Int = 0
)