package com.example.pyxiskapri.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.models.LocationListItem
import com.example.pyxiskapri.models.Post
import kotlinx.android.synthetic.main.item_location.view.*

class LocationListAdapter(private val locationList: MutableList<LocationListItem>) : RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_location, parent, false ) )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.itemView.apply{
            tv_location.text = locationList[position].locationName
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    public fun addLocation(){
        locationList.add(
            LocationListItem(
                1234567,
                "LocationSample"
            )
        )
        notifyItemInserted(locationList.size - 1)
    }

    public fun removeLocation(){
        locationList.removeLastOrNull()
        notifyItemRemoved(locationList.size - 1)
    }

}