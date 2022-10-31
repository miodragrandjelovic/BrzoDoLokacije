package com.example.pyxiskapri.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.pyxiskapri.models.ImageGridItem
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.item_image.view.*

class ImageGridAdapter(var imageItems: MutableList<ImageGridItem>, var context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return imageItems.size
    }

    override fun getItem(position: Int): Any {
        return imageItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_image, parent, false)

        var gvItemImage = view?.findViewById<ImageView>(R.id.iv_image)
        var ibDelete = view?.findViewById<ImageView>(R.id.ib_delete)

        ibDelete?.setOnClickListener {
            removeImage(position)
        }

        gvItemImage?.setImageURI(imageItems[position].uri)


        return view!!
    }

    public fun removeImage(position: Int) {
        imageItems.removeAt(position)
        notifyDataSetChanged()
    }


}