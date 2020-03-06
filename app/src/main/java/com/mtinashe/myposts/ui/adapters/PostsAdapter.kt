package com.mtinashe.myposts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.mtinashe.myposts.R
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import kotlinx.android.synthetic.main.item_posts_layout.view.*


class PostsAdapter(private val context: Context) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private lateinit var items: List<JoinPostData>
    private lateinit var clickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_posts_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = if (::items.isInitialized) items.size else 0

    fun setItems(postList: List<JoinPostData>) {
        this.items = postList
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            if (v != null) {
                clickListener.onItemClick(v, adapterPosition)
            }
        }

        init {
            view.setOnClickListener(this)
        }

        private val tvPostTitle: TextView = view.tv_post_title
        private val tvPostAuthor: TextView = view.tv_post_author
        private val ivPostHeader: ImageView = view.iv_post_header

        fun bind(post: JoinPostData) {
            tvPostTitle.text = post.postTitle
            tvPostAuthor.text = post.authorName
            ivPostHeader.load("https://via.placeholder.com/300")
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): JoinPostData? {
        return items[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
