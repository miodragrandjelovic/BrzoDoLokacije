package com.example.pyxiskapri.utility

import com.example.pyxiskapri.models.PostListItem

interface ActivityTransferStorage {

    companion object {
        @JvmStatic
        var postItemToOpenPost: PostListItem = PostListItem()
    }

}