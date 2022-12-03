package com.example.pyxiskapri.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarker : ClusterItem {

    private val position: LatLng
    private val mTitle: String
    private val mSnippet: String
    private val mIconImage: String

    constructor(lat: Double, lng: Double) {
        position = LatLng(lat, lng)
        mTitle = ""
        mSnippet = ""
        mIconImage = ""
    }

    constructor(lat: Double, lng: Double, title: String, snippet: String,iconImage: String) {
        position = LatLng(lat, lng)
        mTitle = title
        mSnippet = snippet
        mIconImage = iconImage
    }

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }

    fun getIcon(): String {
        return mIconImage
    }

}