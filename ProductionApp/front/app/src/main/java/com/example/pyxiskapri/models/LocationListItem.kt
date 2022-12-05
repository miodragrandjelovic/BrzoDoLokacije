package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.LocationResponse

data class LocationListItem(
    var id: Int,
    var name: String
)
{
    constructor(locationResponse: LocationResponse) : this(locationResponse.id, locationResponse.name)
}
