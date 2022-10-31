package com.example.pyxiskapri.adapters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_post.view.*


class ImageAdapter (private val postList: MutableList<Uri>): RecyclerView.Adapter<ImageAdapter.PostViewHolder>(){

    private var images: ArrayList<Uri?>? = ArrayList()

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        var currentPost = postList[position]
        holder.itemView.apply{
            iv_image.setImageURI(currentPost)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}