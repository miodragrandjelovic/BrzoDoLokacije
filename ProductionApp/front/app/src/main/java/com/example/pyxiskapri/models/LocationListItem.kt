package com.example.pyxiskapri.models

import com.example.pyxiskapri.dtos.response.LocationResponse

data class LocationListItem(
    var id: Int,
    var locationName: String
)
{
    constructor(locationResponse: LocationResponse) : this(locationResponse.id, locationResponse.locationName)
}
