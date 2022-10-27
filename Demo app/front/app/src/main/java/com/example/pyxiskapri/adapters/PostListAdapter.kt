package com.example.pyxiskapri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.Post
import kotlinx.android.synthetic.main.item_post.view.*
import java.time.LocalDate
import java.util.*

class PostListAdapter(private val postList: MutableList<Post>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post, parent, false ) )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPost = postList[position]
        holder.itemView.apply{
            tv_ownerUsername.text = currentPost.ownerUsername
            tv_postCreationDate.text = currentPost.creationDate.toString()
            tv_postDescription.text = currentPost.description
            // IMPLEMENT IMAGE
            // ===========================================================
            iv_like.setColorFilter(R.color.gray)
            iv_dislike.setColorFilter(R.color.gray)
            iv_follow.setColorFilter(R.color.gray)
            iv_report.setColorFilter(R.color.red)
            // CLICK REDO
            // ==========================================================
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    public fun addPost(){
        postList.add(Post(
            "TestOwnerUsername",
            Calendar.getInstance().time,
            "Nibh pellentesque TEST accumsan sapien aliquet tortor. TEST tisali u eleifend mi nunc bibendum malesuada volutpat et TEST non.",
            "",
            false,
            false,
            false,
            false
        ))
        notifyItemInserted(postList.size - 1)
    }

}