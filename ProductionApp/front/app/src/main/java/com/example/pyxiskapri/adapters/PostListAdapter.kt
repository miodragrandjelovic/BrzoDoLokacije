package com.example.pyxiskapri.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*

class PostListAdapter(private val postList: MutableList<PostListItem>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post, parent, false ) )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPost = postList[position]
        holder.itemView.apply{
            tv_ownerUsername.text = currentPost.username
            tv_likeCount.text = currentPost.likeCount.toString()
            tv_viewCount.text = currentPost.viewCount.toString()

            var imageData = android.util.Base64.decode(currentPost.image, android.util.Base64.DEFAULT)
            iv_postImage.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))

            iv_like.setColorFilter(R.color.white)
            iv_dislike.setColorFilter(R.color.white)
            iv_follow.setColorFilter(R.color.white)
            iv_report.setColorFilter(R.color.red)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    public fun setPostList(ownerUsername: String, postResponseList: ArrayList<PostResponse>){
        postList.clear()

        //Log.d("SLIKA", postResponseList[0].image)

        for(post: PostResponse in postResponseList)
            postList.add(PostListItem(post))

        for(post: PostListItem in postList)
            post.username = ownerUsername

        notifyDataSetChanged()
    }

}