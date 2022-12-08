package com.example.pyxiskapri.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileGridActivity
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.dtos.response.FollowUserResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.FollowUserItem
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.follow_item.view.*
import kotlinx.android.synthetic.main.item_post_followed_profiles.view.*

class FollowUserAdapter (private val users: MutableList<FollowUserItem>) : RecyclerView.Adapter<FollowUserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var apiClient: ApiClient

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        apiClient = ApiClient()
        return FollowUserAdapter.UserViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.follow_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentPost = users[position]

        holder.itemView.apply {

            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.profileImage)).into(flw_profileImage)

            flw_first_and_last_name.text = currentPost.firstName + " " + currentPost.lastName

            flw_username.text = currentPost.username

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