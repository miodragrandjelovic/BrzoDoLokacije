package com.example.pyxiskapri.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ForeignProfileGridActivity
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.activities.UserProfile.NewUserProfileActivity
import com.example.pyxiskapri.dtos.response.CustomMarkerResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post_on_same_location.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class PostsOnSameLocationAdapter(private var postList: ArrayList<CustomMarkerResponse>, private var context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return postList.size
    }

    override fun getItem(position: Int): Any {
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    var apiClient: ApiClient = ApiClient()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var currentPost = postList[position]

        var view: View? = convertView
        if(view == null)
            view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_post_on_same_location, parent, false)

        view?.apply{
            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.coverImage)).into(iv_coverImage)
            Picasso.get().load(UtilityFunctions.getFullImagePath(currentPost.profileImagePath)).into(iv_profileImage)

            iv_coverImage.setOnClickListener {
                apiClient.getPostService(context).GetOnePostById(currentPost.postId).enqueue(object : Callback<PostResponse> {
                    override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                        if(response.isSuccessful) {
                            val intent = Intent(context, OpenPostActivity::class.java)
                            ActivityTransferStorage.postItemToOpenPost = response.body()!!
                            context.startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {

                    }
                })
            }

            iv_profileImage.setOnClickListener {
                if(currentPost.ownerUsername != SessionManager(context).fetchUserData()?.username) {
                    val intent = Intent(context, ForeignProfileGridActivity::class.java)
                    intent.putExtra("username", currentPost.ownerUsername)
                    context.startActivity(intent)
                }
                else{
                    val intent = Intent(context, NewUserProfileActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }

        return view!!
    }

    public fun setPostList(postResponseList: ArrayList<CustomMarkerResponse>){
        postList.clear()
        postList.addAll(postResponseList)
        notifyDataSetChanged()
    }

}