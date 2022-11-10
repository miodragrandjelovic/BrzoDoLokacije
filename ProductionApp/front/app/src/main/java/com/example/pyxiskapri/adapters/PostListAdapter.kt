package com.example.pyxiskapri.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.OpenPostActivity
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
        val currentPost = postList[position]
        holder.itemView.apply{
            tv_ownerUsername.text = currentPost.ownerUsername
            tv_likeCount.text = currentPost.likeCount.toString()
            tv_viewCount.text = currentPost.viewCount.toString()

            // val ownerImageData = android.util.Base64.decode(currentPost.ownerImage, android.util.Base64.DEFAULT)
            // iv_ownerAvatar.setImageBitmap(BitmapFactory.decodeByteArray(ownerImageData, 0, ownerImageData.size))

            val imageData = android.util.Base64.decode(currentPost.coverImage, android.util.Base64.DEFAULT)
            iv_postImage.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))

            iv_postImage.setOnClickListener{
                val intent = Intent(context, OpenPostActivity::class.java)


                intent.putExtra("postData", currentPost)

//                intent.putExtra("postId", currentPost.id)
//                intent.putExtra("ownerUsername", currentPost.ownerUsername)
//                intent.putExtra("ownerImage", currentPost.ownerImage)
//                intent.putExtra("coverImage", currentPost.coverImage)
//                intent.putExtra("numberOfLikes", currentPost.likeCount)
//                intent.putExtra("numberOfView", currentPost.viewCount)

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    public fun setPostList(postResponseList: ArrayList<PostResponse>){
        postList.clear()

        for(post: PostResponse in postResponseList)
            postList.add(PostListItem(post))

        notifyDataSetChanged()
    }

}