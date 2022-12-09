package com.example.pyxiskapri.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.models.ImageGridItem
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_full_image.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.android.synthetic.main.item_pickable_cover_image.view.*
import kotlinx.android.synthetic.main.item_post_image.view.*
import kotlinx.android.synthetic.main.item_post_image.view.iv_image


class CoverPickImagesAdapter(private var images: MutableList<ImageGridItem>, private var itemClickListener: (selectedCoverIndex: Int) -> Unit) : RecyclerView.Adapter<CoverPickImagesAdapter.PostImageViewHolder>() {

    private var selectedCoverIndex = 0

    class PostImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImageViewHolder {
        return PostImageViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_pickable_cover_image, parent, false ) )
    }

    override fun onBindViewHolder(holder: PostImageViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentImage = images[position]

        holder.itemView.apply{
            iv_image.setImageURI(currentImage.uri)

            if(position == selectedCoverIndex)
                iv_checkmark.visibility = View.VISIBLE
            else
                iv_checkmark.visibility = View.INVISIBLE

            iv_image.setOnClickListener {
                itemClickListener(position)
                selectedCoverIndex = position
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    public fun setImages(newImages: MutableList<ImageGridItem>, selectedIndex: Int){
        images = newImages
        selectedCoverIndex = selectedIndex
        notifyDataSetChanged()
    }

}