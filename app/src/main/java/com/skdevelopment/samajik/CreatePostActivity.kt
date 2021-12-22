package com.skdevelopment.samajik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skdevelopment.samajik.doas.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postDao = PostDao()

        postBtn.setOnClickListener{
            val input = postText.text.toString().trim()
            if(input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }
        }
    }


}