package com.example.pyxiskapri.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileGridActivity
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.dtos.request.AddFollowRequest
import com.example.pyxiskapri.dtos.response.FollowUserResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.FollowUserItem
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_foreign_profile_grid.*
import kotlinx.android.synthetic.main.follow_item.view.*
import kotlinx.android.synthetic.main.item_post_followed_profiles.view.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowUserAdapter (private val users: MutableList<FollowUserItem>) : RecyclerView.Adapter<FollowUserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var apiClient: ApiClient

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        apiClient = ApiClient()
        return UserViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.follow_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentPost = users[position]

        holder.itemView.apply {

            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.profileImage)).into(flw_profileImage)

            flw_first_and_last_name.text = currentPost.firstName + " " + currentPost.lastName

            flw_username.text = currentPost.username

            if(currentPost.username==SessionManager(context).fetchUserData()?.username)
            {
                btn_follow.isGone=true
                btn_following.isGone=true
            }
            else if(currentPost.isFollowed)
            {
                btn_follow.isGone=true
                btn_following.isGone=false
            }
            else
            {
                btn_follow.isGone=false
                btn_following.isGone=true
            }

            //follow
            btn_follow.setOnClickListener(){

                val addFollowRequest= AddFollowRequest(
                    username = currentPost.username
                )

                apiClient.getUserService(context).follow(addFollowRequest).enqueue(object : Callback<MessageResponse>{
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {

                        if(response.isSuccessful)
                        {
                            btn_follow.isGone=true
                            btn_following.isGone=false
                        }
                        else
                        {
                            Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                        Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()

                    }

                })
            }


            btn_following.setOnClickListener(){

                apiClient.getUserService(context).unfollow(currentPost.username).enqueue(object : Callback<MessageResponse>{
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {

                        if(response.isSuccessful)
                        {
                            btn_follow.isGone=false
                            btn_following.isGone=true
                        }

                        else
                        {
                            Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()

                        }

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                        Toast.makeText(context,"Failure, try again.", Toast.LENGTH_LONG).show()

                    }

                })
            }


            follow_item.setOnClickListener(){

                if(currentPost.username== SessionManager(context).fetchUserData()?.username)
                {
                    val intent = Intent(context, NewUserProfileActivity::class.java)
                    context.startActivity(intent)
                }
                else
                {
                    val intent = Intent(context, ForeignProfileGridActivity::class.java)
                    intent.putExtra("username", currentPost.username)
                    context.startActivity(intent)
                }

            }

        }
    }



    override fun getItemCount(): Int {

        return users.size
    }


    fun setFollowUsersList(followUserPostList: ArrayList<FollowUserResponse>){
        users.clear()

        for(post: FollowUserResponse in followUserPostList)
            users.add(FollowUserItem(post))

        notifyDataSetChanged()
    }


}