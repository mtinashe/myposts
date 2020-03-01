package com.mtinashe.myposts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.adapters.PostsAdapter
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
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

        val adapter = PostsAdapter(this)
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter

        postViewModel.allPosts.observe(this, Observer {
            adapter.setItems(it)
        })
    }
}
