package com.example.pyxiskapri.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.isGone
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.PostListItem
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient


class gvForeignPostAdapter (var postsItem: MutableList<PostListItem>, var context: Context) : BaseAdapter() {


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
            view = layoutInflater.inflate(R.layout.item_image, parent, false)


        var gvItemImage = view?.findViewById<ImageView>(R.id.iv_image)
        var ibDelete = view?.findViewById<ImageView>(R.id.ib_delete)


        ibDelete?.isGone=true



        val picture=postsItem[position].coverImage
        if(picture!=null)
        {
            var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
            gvItemImage?.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
        }


        gvItemImage?.setOnClickListener(){
            val intent = Intent(context, OpenPostActivity::class.java)
            Log.d("BASE 64", postsItem[position].coverImage)
            //intent.putExtra("postData", currentPost)
            ActivityTransferStorage.postItemToOpenPost = postsItem[position]
            context.startActivity(intent)

            (context as Activity).finish()

        }


        return view!!
    }



    fun setPostList(postResponseList: ArrayList<PostResponse>){
        postsItem.clear()

        for(post: PostResponse in postResponseList)
            postsItem.add(PostListItem(post))

        notifyDataSetChanged()
    }

}