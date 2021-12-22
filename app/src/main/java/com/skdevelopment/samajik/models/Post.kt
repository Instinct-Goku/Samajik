package com.skdevelopment.samajik.models

data class Post(
    val createdBy : User = User(),
    val postText : String = "",
    val time: Long? = 0L,
    val likedBy: ArrayList<String> = ArrayList()
)