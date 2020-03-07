package com.mtinashe.myposts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtinashe.myposts.R
import com.mtinashe.myposts.data.entities.Comment
import kotlinx.android.synthetic.main.item_comments_layout.view.*

class CommentsAdapter(val context: Context) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private lateinit var items: List<Comment>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_comments_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = if (::items.isInitialized) items.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(comments: List<Comment>) {
        this.items = comments
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val tvCommentAuthor: TextView = view.tv_comment_name
        private val tvCommentBody: TextView = view.tv_comment_body

        fun bind(comment: Comment) {
            tvCommentAuthor.text = comment.name
            tvCommentBody.text = comment.body
        }
    }
}
