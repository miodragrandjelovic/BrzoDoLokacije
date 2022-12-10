package com.example.pyxiskapri.utility

import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.models.ChangeCredentialsInformation
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.models.MarkerModel
import com.google.android.gms.maps.model.LatLng

interface ActivityTransferStorage {

    companion object {
        @JvmStatic
        lateinit var postItemToOpenPost: PostResponse

        @JvmStatic
        lateinit var openPostToMap: MarkerModel

        @JvmStatic
        var flag:Boolean = false

        @JvmStatic
        lateinit var chatToChatting: FriendResponse

        @JvmStatic
        lateinit var changeCredentialsInformation: ChangeCredentialsInformation

        @JvmStatic
        lateinit var followList : FollowList
    }

}