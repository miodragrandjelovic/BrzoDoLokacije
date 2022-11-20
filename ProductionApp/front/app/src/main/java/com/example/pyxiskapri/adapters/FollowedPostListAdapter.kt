package com.example.pyxiskapri.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileActivity
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.item_post_followed_profiles.view.*


class FollowedPostListAdapter (private val postList: MutableList<PostListItem>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>(){

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var apiClient: ApiClient

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostListAdapter.PostViewHolder {

        apiClient = ApiClient()
        return PostListAdapter.PostViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_post_followed_profiles, parent, false))
    }

    override fun onBindViewHolder(holder: PostListAdapter.PostViewHolder, position: Int) {

        val currentPost = postList[position]
        holder.itemView.apply {
            profileImage.setImageBitmap(UtilityFunctions.base64ToBitmap(currentPost.ownerImage))
            //tv_ownerUsername.text = currentPost.ownerUsername
            f_post_likes.text = currentPost.likeCount.toString()
            f_post_dislikes.text = currentPost.viewCount.toString()


            val imageView: ShapeableImageView = findViewById(com.example.pyxiskapri.R.id.imagePost)
            val radius = resources.getDimension(com.example.pyxiskapri.R.dimen.corner_radius)
            imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
                .toBuilder().setAllCornerSizes(radius)
                //.setTopRightCorner(CornerFamily.ROUNDED,10.0)
                .build()



            imagePost.setImageBitmap(UtilityFunctions.base64ToBitmap(currentPost.coverImage))

            imagePost.setOnClickListener{
                    val intent = Intent(context, OpenPostActivity::class.java)
                    Log.d("BASE 64", currentPost.coverImage)
                    //intent.putExtra("postData", currentPost)
                    ActivityTransferStorage.postItemToOpenPost = currentPost
                    context.startActivity(intent)
                }


            profileImage.setOnClickListener(){

                val intent = Intent(context, ForeignProfileActivity::class.java)
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

        for(post: PostResponse in postResponseList)
            postList.add(PostListItem(post))

        notifyDataSetChanged()
    }


}