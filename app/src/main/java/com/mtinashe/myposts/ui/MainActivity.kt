package com.mtinashe.myposts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel : PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)

        postViewModel.allPosts.observe(this, Observer {
            it.forEach {post ->
                Log.d("MainActivity", post.title)
            }
        })
    }
}
