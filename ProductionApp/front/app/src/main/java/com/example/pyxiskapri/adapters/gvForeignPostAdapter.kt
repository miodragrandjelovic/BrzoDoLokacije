package com.example.pyxiskapri.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.isGone
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class gvForeignPostAdapter (var postsItem: MutableList<PostResponse>, var context: Context) : BaseAdapter() {


    private var apiClient: ApiClient = ApiClient()

    override fun getCount(): Int {
        return postsItem.size
    }

    override fun getItem(position: Int): Any {
        return postsItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.grid_view_layout, parent, false)


        var gvItemImage = view?.findViewById<ShapeableImageView>(R.id.siv_imagePost)
        var ibDelete = view?.findViewById<ImageView>(R.id.ib_delete_post)


        ibDelete?.isGone=true


        val picture=postsItem[position].coverImage
        if(picture!=null)
        {
            Picasso.get().load(UtilityFunctions.getFullImagePath(postsItem[position].coverImage)).into(gvItemImage)

            val radius = context.resources.getDimension(com.example.pyxiskapri.R.dimen.corner_radius20dp)
            gvItemImage?.shapeAppearanceModel = gvItemImage?.shapeAppearanceModel!!
                .toBuilder().setAllCornerSizes(radius)
                .build()

        }


        gvItemImage?.setOnClickListener(){
            val intent = Intent(context, OpenPostActivity::class.java)
            ActivityTransferStorage.postItemToOpenPost = postsItem[position]
            context.startActivity(intent)

            (context as Activity).finish()

        }


        return view!!
    }



    fun setPostList(postResponseList: ArrayList<PostResponse>){
        postsItem.clear()
        postsItem.addAll(postResponseList)

        notifyDataSetChanged()
    }

}