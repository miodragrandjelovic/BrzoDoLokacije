package com.example.pyxiskapri.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileGridActivity
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post_followed_profiles.view.*


class FollowedPostListAdapter (private val postList: MutableList<PostResponse>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>(){

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var apiClient: ApiClient

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.PostViewHolder {
        apiClient = ApiClient()
        return PostListAdapter.PostViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_post_followed_profiles, parent, false))
    }

    override fun onBindViewHolder(holder: PostListAdapter.PostViewHolder, position: Int) {

        val currentPost = postList[position]
        holder.itemView.apply {
            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.ownerImage)).into(profileImage)
            f_post_likes.text = currentPost.likeCount.toString()
            f_post_dislikes.text = currentPost.viewCount.toString()


            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.coverImage)).into(iv_coverImage)

            iv_coverImage.setOnClickListener{
                    val intent = Intent(context, OpenPostActivity::class.java)
                    ActivityTransferStorage.postItemToOpenPost = currentPost
                    context.startActivity(intent)
                }


            profileImage.setOnClickListener(){
                val intent = Intent(context, ForeignProfileGridActivity::class.java)
                intent.putExtra("username", currentPost.ownerUsername)
                context.startActivity(intent)
            }


        }
    }

    override fun getItemCount(): Int
    {
        return postList.size
    }

    fun setFollowedPostList(postResponseList: ArrayList<PostResponse>){
        postList.clear()
        postList.addAll(postResponseList)

        notifyDataSetChanged()
    }


}