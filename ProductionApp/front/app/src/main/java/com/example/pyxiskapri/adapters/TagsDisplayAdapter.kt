package com.example.pyxiskapri.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.MapActivity
import com.example.pyxiskapri.utility.ActivityTransferStorage
import kotlinx.android.synthetic.main.item_tag.view.*

class TagsDisplayAdapter(var tagsList: ArrayList<String> = arrayListOf(), var context: Context) : RecyclerView.Adapter<TagsDisplayAdapter.TagViewHolder>() {
    class TagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.itemView.apply{
            tv_tagName.text = tagsList[position]

            this.setOnClickListener{
                val intent = Intent (context, MapActivity::class.java);
                ActivityTransferStorage.openTagSearch = true
                ActivityTransferStorage.tag = tagsList[position]
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    public fun setTags(tagsString: String){
        if(tagsString == "")
            return

        tagsList.clear()
        tagsList.addAll(tagsString.split(", "))
        notifyDataSetChanged()
    }


}