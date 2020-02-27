package com.mtinashe.myposts.api.entities


import com.google.gson.annotations.SerializedName

data class Author(
    var email: String = "",
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var username: String = "",
    var website: String = ""
)