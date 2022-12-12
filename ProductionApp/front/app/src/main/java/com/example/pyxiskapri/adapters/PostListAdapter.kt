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
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*


class PostListAdapter(private var postList: MutableList<PostResponse>) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        apiClient = ApiClient()
        return PostViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post, parent, false ) )
    }

    lateinit var apiClient: ApiClient

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList[position]

        holder.itemView.apply{
            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.ownerImage)).into(iv_ownerAvatar)
            tv_ownerUsername.text = currentPost.ownerUsername

            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.coverImage)).into(iv_postImage)

            iv_postImage.setOnClickListener{
                val intent = Intent(context, OpenPostActivity::class.java)
                ActivityTransferStorage.postItemToOpenPost = currentPost
                context.startActivity(intent)
            }

            gradeDisplay.setGradeDisplay(currentPost.averageGrade, currentPost.gradesCount)
            gradeSelector.setGradeSelectorInitialGrade(currentPost.usersGrade)
            gradeSelector.gradeDisplay = gradeDisplay
            gradeSelector.gradedPostId = currentPost.id

            gradeSelector.setOnGradeSelectListener {
                currentPost.averageGrade = it.averageGrade
                currentPost.gradesCount = it.gradesCount
                currentPost.usersGrade = gradeSelector.grade
            }

            btn_ForeignUser.setOnClickListener(){

                val intent = Intent(context, ForeignProfileGridActivity::class.java)
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
        postList.addAll(postResponseList)
        notifyDataSetChanged()
    }

//    private fun setRemoveLike(currentPost: PostListItem, position: Int, context: Context, isPostLiked: Boolean){
//        if(isPostLiked) {
//            apiClient.getPostService(context).removeLike(currentPost.id)
//                .enqueue(object : Callback<MessageResponse> {
//                    override fun onResponse(
//                        call: Call<MessageResponse>,
//                        response: Response<MessageResponse>
//                    ) {
//                        if(response.isSuccessful) {
//                            postList[position].isLiked = false
//                            postList[position].likeCount -= 1
//                            notifyItemChanged(position)
//                        }
//
//                    }
//
//                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
//                        Log.d(
//                            "PostListAdapter",
//                            "Nije implementiran onFailure za removeLike api zahtev!"
//                        )
//                    }
//
//                })
//        }
//        else {
//            apiClient.getPostService(context).setLike(currentPost.id)
//                .enqueue(object : Callback<MessageResponse> {
//                    override fun onResponse(
//                        call: Call<MessageResponse>,
//                        response: Response<MessageResponse>
//                    ) {
//                        if(response.isSuccessful) {
//                            postList[position].isLiked = true
//                            postList[position].likeCount += 1
//                            notifyItemChanged(position)
//                        }
//
//                    }
//
//                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
//                        Log.d(
//                            "PostListAdapter",
//                            "Nije implementiran onFailure za setLike api zahtev!"
//                        )
//                    }
//
//                })
//        }
//    }

}