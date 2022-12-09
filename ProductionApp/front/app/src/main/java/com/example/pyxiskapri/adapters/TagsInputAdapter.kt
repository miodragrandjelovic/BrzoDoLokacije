package com.example.pyxiskapri.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.item_tag.view.*

class TagsInputAdapter(var tagsList: MutableList<String>, var context: Context) : RecyclerView.Adapter<TagsInputAdapter.TagViewHolder>() {
    class TagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.itemView.apply{
            tv_tagName.text = tagsList[position]

            this.setOnClickListener{
                tagsList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    public fun addTag(tag: String){
        tagsList.add(tag)
        notifyDataSetChanged()
    }


}