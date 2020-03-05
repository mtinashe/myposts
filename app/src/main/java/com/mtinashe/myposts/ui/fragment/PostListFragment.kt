package com.mtinashe.myposts.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtinashe.myposts.R
import com.mtinashe.myposts.ui.adapters.PostsAdapter
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import kotlinx.android.synthetic.main.post_list_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PostListFragment : Fragment(R.layout.post_list_fragment), KodeinAware, PostsAdapter.ItemClickListener {

    override val kodein: Kodein by kodein()
    private val viewModelFactory : PostsViewModelFactory by instance()
    private lateinit var postViewModel: PostsViewModel
    private lateinit var adapter : PostsAdapter

    companion object {
        fun newInstance() = PostListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postViewModel = ViewModelProviders.of(this,viewModelFactory).get(PostsViewModel::class.java)

        adapter = PostsAdapter(requireContext())
        adapter.setClickListener(this)
        rv_posts.layoutManager = LinearLayoutManager(requireContext())
        rv_posts.adapter = adapter

        postViewModel.allPosts.observe(requireActivity(), Observer {
            adapter.setItems(it)
        })
    }

    override fun onItemClick(view: View, position: Int) {
        adapter.getItem(position)?.run {
            findNavController().navigate(PostListFragmentDirections.actionPostListFragmentToArticleFragment(this.postId))
        }

    }
}
