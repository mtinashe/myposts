package com.mtinashe.myposts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    private val viewModelFactory : PostsViewModelFactory by instance()
    private lateinit var postViewModel : PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postViewModel = ViewModelProviders.of(this,viewModelFactory).get(PostsViewModel::class.java)

        postViewModel.allPosts.observe(this, Observer {
            it.forEach {post ->
//                Log.d("MainActivity",post.title)
            }
        })

        postViewModel.allCommentsByPost.observe(this, Observer {
            it.forEach {comment ->
                Log.d("MainActivity",comment.body)
            }
        })

        postViewModel.authorById.observe(this, Observer {
            Log.d("MainActivity",it.name)
        })
    }
}
