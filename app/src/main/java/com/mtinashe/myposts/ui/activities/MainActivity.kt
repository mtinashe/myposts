package com.mtinashe.myposts.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import kotlinx.android.synthetic.main.article_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    val viewModelFactory : PostsViewModelFactory by instance()
    lateinit var viewModel : PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostsViewModel::class.java)
        viewModel.startSync()
    }
}
