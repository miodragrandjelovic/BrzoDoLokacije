package com.example.pyxiskapri.adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.OpenPostActivity
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.modal_confirm_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPostsAdapter (var postsItem: MutableList<PostResponse>, var context: Context, var onPostDeleteListener: () -> Unit) : BaseAdapter() {

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

        ibDelete?.setOnClickListener {
            removeImage(position)
        }


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




    fun removeImage(position: Int) {

        val dialog= Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_delete)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.show()

        dialog.btn_confirm_delete.setOnClickListener(){

            apiClient.getPostService(context).removePost(postsItem[position].id).enqueue(object : Callback<MessageResponse>{
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        postsItem.removeAt(position)
                        notifyDataSetChanged()
                        Toast.makeText(context,"Post is successfully deleted!",Toast.LENGTH_LONG).show()
                        onPostDeleteListener()
                    }

                    else
                    {
                        Toast.makeText(context,"Post cannot be deleted.",Toast.LENGTH_LONG).show()
                    }

                    dialog.dismiss()
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            })
        }


        dialog.btn_close_dialog.setOnClickListener(){
            dialog.dismiss()
        }



    }

    fun setPostList(postResponseList: ArrayList<PostResponse>){
        postsItem.clear()
        postsItem.addAll(postResponseList)

        notifyDataSetChanged()
    }


}