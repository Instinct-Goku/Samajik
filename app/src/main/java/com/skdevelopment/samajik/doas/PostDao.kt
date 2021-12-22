package com.skdevelopment.samajik.doas

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.skdevelopment.samajik.models.Post
import com.skdevelopment.samajik.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

     val db = FirebaseFirestore.getInstance()

    val postCollection = db.collection("posts")

    val auth = Firebase.auth

    fun addPost(text : String) {

        GlobalScope.launch {
            val curUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            val user = userDao.getUserById(curUserId).await().toObject(User::class.java)!!

            val curTime = System.currentTimeMillis()

            val post = Post(user, text, curTime)

            postCollection.document().set(post)
        }

    }

    fun getPostId(postId: String) : Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLike(postId: String) {
        GlobalScope.launch {
            val curUserId = auth.currentUser!!.uid
            val post = getPostId(postId).await().toObject(Post::class.java)
            val isLiked = post!!.likedBy.contains(curUserId)

            if(isLiked)
                post.likedBy.remove(curUserId)
            else
                post.likedBy.add(curUserId)

            postCollection.document(postId).set(post)
        }

    }
}