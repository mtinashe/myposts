package com.mtinashe.myposts.data.entities


data class Comment(
    var body: String = "",
    var email: String = "",
    var id: Int = 0,
    var name: String = "",
    var postId: Int = 0
)