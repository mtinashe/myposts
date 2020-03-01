package com.mtinashe.myposts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtinashe.myposts.R
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import kotlinx.android.synthetic.main.item_posts_layout.view.*


class PostsAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var items : List<JoinPostData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_posts_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPostTitle.text = items[position].postTitle
        holder.tvPostAuthor.text = items[position].authorName
    }

    override fun getItemCount() = if (::items.isInitialized) items.size else 0

    fun setItems(postList : List<JoinPostData>){
        this.items = postList
        notifyDataSetChanged()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvPostTitle: TextView = view.tv_post_title
    val tvPostAuthor: TextView = view.tv_post_author
}