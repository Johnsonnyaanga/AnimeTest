package com.johnson.myapplication.data.image

data class ImageSearchResponse(
    val error: String,
    val frameCount: Int,
    val result: List<Result>
)