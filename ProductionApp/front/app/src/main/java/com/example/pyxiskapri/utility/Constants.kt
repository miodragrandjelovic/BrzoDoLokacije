package com.example.pyxiskapri.utility

object Constants {
    const val BASE_URL: String = "http://10.0.2.2:5231"
    //const val BASE_URL: String = "http://softeng.pmf.kg.ac.rs:10030"
    const val SHAREDPREFS_PATH: String = "com.example.pyxiskapri"

    // Kodovi
    const val CODE_BAD_REQUEST: Int = 400
    const val CODE_UNAUTHORIZED: Int = 401
    // Experimental
    const val CODE_PAYMENT_REQUIRED: Int = 402
    const val CODE_FORBIDDEN: Int = 403
    const val CODE_NOT_FOUND: Int = 404
    const val CODE_METHOD_NOT_ALLOWED: Int = 405
    const val CODE_NOT_ACCEPTABLE: Int = 406

    enum class SortType(){ DATE, COUNT_LIKES, COUNT_COMMENTS, COUNT_VIEWS }
    enum class SearchType(){ LOCATION, COORDINATES, CITY, COUNTRY }

}