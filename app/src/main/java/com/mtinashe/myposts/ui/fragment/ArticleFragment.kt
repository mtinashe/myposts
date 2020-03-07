package com.mtinashe.myposts.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.mtinashe.myposts.R
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import com.mtinashe.myposts.ui.adapters.CommentsAdapter
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import com.mtinashe.myposts.utils.StringUtils
import kotlinx.android.synthetic.main.article_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ArticleFragment : Fragment(R.layout.article_fragment), KodeinAware {

    override val kodein: Kodein by kodein()

    private lateinit var viewModel: PostsViewModel
    private val viewModelFactory: PostsViewModelFactory by instance()
    private lateinit var commentsAdapter: CommentsAdapter
    private var postId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)
        arguments?.run {
            ArticleFragmentArgs.fromBundle(this).id
        }?.let { postId = it }

        viewModel.setPostId(postId)

        commentsAdapter = CommentsAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ArticleFragmentOne", postId.toString())

        viewModel.post.observe(viewLifecycleOwner, Observer { post ->
            bindUi(post)
        })

        viewModel.comments.observe(viewLifecycleOwner, Observer { comments ->
            tv_comments.text = getString(R.string.title_comments, comments.size)
            setRecyclerItems(comments)
        })

        // recycler view
        rv_comments.layoutManager = LinearLayoutManager(requireContext())
        rv_comments.adapter = commentsAdapter

        iv_back.setOnClickListener {
            findNavController().popBackStack()
        }

        tv_comments.setOnClickListener {
            with(rv_comments) {
                if (this.isVisible) {
                    rv_comments.visibility = View.GONE
                } else {
                    rv_comments.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindUi(post: JoinPostData) {
        tv_post_author.text = post.authorName
        tv_title.text = StringUtils.capitalize(post.postTitle)
        tv_post_body.text = StringUtils.capitalize(post.postBody)

        iv_post_image.load("https://via.placeholder.com/600x200") {
            this.placeholder(R.drawable.ic_launcher_foreground)
        }

        iv_avatar.load("https://api.adorable.io/avatars/111/abott@adorable.png")
    }

    private fun setRecyclerItems(comments: List<Comment>) {
        if (::commentsAdapter.isInitialized) commentsAdapter.setItems(comments)
    }
}
