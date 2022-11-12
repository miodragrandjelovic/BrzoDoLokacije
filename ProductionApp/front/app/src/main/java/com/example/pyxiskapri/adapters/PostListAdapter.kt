package com.example.pyxiskapri.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.graphics.PorterDuff
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileActivity
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.item_post.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PostListAdapter(private val postList: MutableList<PostListItem>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        apiClient = ApiClient()
        return PostViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post, parent, false ) )
    }

    lateinit var apiClient: ApiClient

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.itemView.apply{
            iv_ownerAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(currentPost.ownerImage))
            tv_ownerUsername.text = currentPost.ownerUsername
            tv_likeCount.text = currentPost.likeCount.toString()
            tv_viewCount.text = currentPost.viewCount.toString()

            // Liked
            if(currentPost.isLiked)
                iv_likeIcon.setColorFilter(ContextCompat.getColor(context, R.color.gold), PorterDuff.Mode.SRC_IN);
            else
                iv_likeIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);

            iv_postImage.setImageBitmap(UtilityFunctions.base64ToBitmap(currentPost.coverImage))

            iv_postImage.setOnClickListener{
                val intent = Intent(context, OpenPostActivity::class.java)
                Log.d("BASE 64", currentPost.coverImage)
                //intent.putExtra("postData", currentPost)
                ActivityTransferStorage.postItemToOpenPost = currentPost
                context.startActivity(intent)
            }

            btn_like.setOnClickListener {
                setRemoveLike(currentPost, position, context, currentPost.isLiked)
            }

            btn_ForeignUser.setOnClickListener(){
                val intent = Intent(context, ForeignProfileActivity::class.java)
                intent.putExtra("username", tv_ownerUsername.text.toString())
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

    private fun setRemoveLike(currentPost: PostListItem, position: Int, context: Context, isPostLiked: Boolean){
        if(isPostLiked) {
            apiClient.getPostService(context).removeLike(currentPost.id)
                .enqueue(object : Callback<MessageResponse> {
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if(response.isSuccessful) {
                            postList[position].isLiked = false
                            postList[position].likeCount -= 1
                            notifyItemChanged(position)
                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Log.d(
                            "PostListAdapter",
                            "Nije implementiran onFailure za removeLike api zahtev!"
                        )
                    }

                })
        }
        else {
            apiClient.getPostService(context).setLike(currentPost.id)
                .enqueue(object : Callback<MessageResponse> {
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if(response.isSuccessful) {
                            postList[position].isLiked = true
                            postList[position].likeCount += 1
                            notifyItemChanged(position)
                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Log.d(
                            "PostListAdapter",
                            "Nije implementiran onFailure za setLike api zahtev!"
                        )
                    }

                })
        }
    }

}