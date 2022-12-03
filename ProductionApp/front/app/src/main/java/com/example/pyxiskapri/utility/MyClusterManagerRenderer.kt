package com.example.pyxiskapri.utility


import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.models.ClusterMarker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator


class MyClusterManagerRenderer : DefaultClusterRenderer<ClusterMarker> {


    private val iconGenerator:IconGenerator
    private val imageView:ImageView
    private val markerWidth:Int
    private val markerHeight:Int


    constructor(
        context: Context?, map: GoogleMap?, clusterManager: ClusterManager<ClusterMarker>?)
            : super(context, map, clusterManager)
    {
        iconGenerator = IconGenerator(context!!.applicationContext)
        imageView = ImageView(context!!.applicationContext)

        markerWidth = context!!.resources.getDimension(R.dimen.custom_marker_image).toInt()
        markerHeight = context!!.resources.getDimension(R.dimen.custom_marker_image).toInt()
        imageView.layoutParams = ViewGroup.LayoutParams(markerWidth, markerHeight)
        val padding =
            context!!.resources.getDimension(R.dimen.padding).toInt()
        imageView.setPadding(padding, padding, padding, padding)
        iconGenerator.setContentView(imageView)

    }

    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
        imageView.setImageURI()
        val icon = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
    }

}