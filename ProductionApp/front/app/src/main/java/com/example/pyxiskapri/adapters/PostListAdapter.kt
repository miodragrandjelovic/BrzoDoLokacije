package com.example.pyxiskapri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.models.Post
import kotlinx.android.synthetic.main.item_post.view.*
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
            tv_likeCount.text = currentPost.likeCount.toString()
            tv_viewCount.text = currentPost.viewCount.toString()
            // IMPLEMENT IMAGE
            // ===========================================================
            iv_like.setColorFilter(R.color.white)
            iv_dislike.setColorFilter(R.color.white)
            iv_follow.setColorFilter(R.color.white)
            iv_report.setColorFilter(R.color.red)
            // CLICK REDO
            // ==========================================================
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    public fun addPost(){
        postList.add(
            Post(
                "TestOwnerUsername",
                "img",
                1234567,
                1234567,
                false,
                false,
                false,
                false
            )
        )
        notifyItemInserted(postList.size - 1)
    }

}