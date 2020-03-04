package com.mtinashe.myposts.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.viewmodels.ArticleViewModel
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import kotlinx.android.synthetic.main.article_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ArticleFragment : Fragment(R.layout.article_fragment), KodeinAware {

    override val kodein: Kodein by kodein()

    private lateinit var viewModel: PostsViewModel
    private val viewModelFactory : PostsViewModelFactory by instance()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PostsViewModel::class.java)

        iv_back.setOnClickListener{
            findNavController().popBackStack()
        }

        arguments?.let {
            val postId = ArticleFragmentArgs.fromBundle(it).id
            tv_post_title.text = postId.toString()
        }

    }
}
