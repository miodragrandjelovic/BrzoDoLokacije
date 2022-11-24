package com.example.pyxiskapri.utility

import com.example.pyxiskapri.TransferModels.PostItemToOpenPost
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.models.PostListItem
import com.google.android.gms.maps.model.LatLng

interface ActivityTransferStorage {

    companion object {
        @JvmStatic
        lateinit var postItemToOpenPost: PostItemToOpenPost

        @JvmStatic
        lateinit var openPostToMap: LatLng

        @JvmStatic
        lateinit var chatToChatting: FriendResponse
    }

}