package com.example.pyxiskapri.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.models.LocationListItem
import kotlinx.android.synthetic.main.item_location.view.*

class LocationListAdapter(private val locationList: ArrayList<LocationListItem>, private val onItemClicked: (id: Int, name: String) -> Unit) : RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_location, parent, false ) )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.itemView.apply{
            tv_location.text = locationList[position].name

            this.setOnClickListener{
                onItemClicked(locationList[position].id, locationList[position].name)
            }
        }

    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun setLocations(locationResponseList: ArrayList<LocationResponse>){
        locationList.clear()
        for(response: LocationResponse in locationResponseList)
            locationList.add(LocationListItem(response))
        notifyDataSetChanged()
    }

    public fun clearLocations(){
        locationList.clear()
    }

}