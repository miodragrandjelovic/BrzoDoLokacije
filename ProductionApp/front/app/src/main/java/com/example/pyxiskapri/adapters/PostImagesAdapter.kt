package com.example.pyxiskapri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post_image.view.*

class PostImagesAdapter(private val images: MutableList<Pair<String, String>>) : RecyclerView.Adapter<PostImagesAdapter.PostImageViewHolder>() {
    class PostImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImageViewHolder {
        apiClient = ApiClient()
        return PostImageViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post_image, parent, false ) )
    }

    lateinit var apiClient: ApiClient

    override fun onBindViewHolder(holder: PostImageViewHolder, position: Int) {
        val currentImage = images[position]

        holder.itemView.apply{
            Picasso.get().load(UtilityFunctions.getFullImagePath("Images/" + currentImage.first + "/" + currentImage.second)).into(iv_image)

            iv_image.setOnClickListener {
                // Fullscreen Image
            }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}