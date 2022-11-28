package com.example.pyxiskapri.adapters

import android.app.Dialog
import android.graphics.Color
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_full_image.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.android.synthetic.main.item_post_image.view.*


class PostImagesAdapter(private val images: MutableList<String>) : RecyclerView.Adapter<PostImagesAdapter.PostImageViewHolder>() {
    class PostImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImageViewHolder {
        return PostImageViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_post_image, parent, false ) )
    }

    override fun onBindViewHolder(holder: PostImageViewHolder, position: Int) {
        val currentImage = images[position]

        holder.itemView.apply{
            Picasso.get().load(UtilityFunctions.getFullImagePath(currentImage)).into(iv_image)

            iv_image.setOnClickListener {
                val dialog = Dialog(context)

                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_full_image)
                dialog.window?.attributes = layoutParams

                dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                dialog.show()

                Picasso.get().load(UtilityFunctions.getFullImagePath(currentImage)).into(dialog.iv_fullImage)

                dialog.btn_closeDialog.setOnClickListener {
                    dialog.dismiss()
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}