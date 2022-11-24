package com.example.pyxiskapri.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.TransferModels.PostItemToOpenPost
import com.example.pyxiskapri.activities.ForeignProfileActivity
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_post.view.*
import kotlinx.android.synthetic.main.fragment_drawer_nav.view.*
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.net.URL
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
            val ownerImage = Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.ownerImage)).get()
            iv_ownerAvatar.setImageBitmap(ownerImage)
            tv_ownerUsername.text = currentPost.ownerUsername
            tv_likeCount.text = currentPost.likeCount.toString()
            tv_viewCount.text = currentPost.viewCount.toString()

            // Liked
            if(currentPost.isLiked)
                iv_likeIcon.setColorFilter(ContextCompat.getColor(context, R.color.gold), PorterDuff.Mode.SRC_IN);
            else
                iv_likeIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);

            val coverImage = Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.coverImage)).get()

            iv_coverImage.setImageBitmap(coverImage)

            iv_postImage.setOnClickListener{
                val intent = Intent(context, OpenPostActivity::class.java)
                ActivityTransferStorage.postItemToOpenPost = PostItemToOpenPost(currentPost, ownerImage, coverImage)
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