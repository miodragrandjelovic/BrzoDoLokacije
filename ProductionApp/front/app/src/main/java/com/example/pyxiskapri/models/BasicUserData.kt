package com.example.pyxiskapri.models

data class BasicUserData(
    private var profileImage: String,
    private var username: String,
    private var firstName: String,
    private var lastName: String
){
    constructor(basicUserData: BasicUserData) : this(basicUserData.profileImage, basicUserData.username, basicUserData.firstName, basicUserData.lastName)
}
