package com.cool.myfashion.model

import com.squareup.moshi.Json

/**
 * Created by rahul.p
 *
 */
data class Content(
    val type: Type,
    val cols: Int,
    val show: Int,
    val title: String,
    val url: String,
    var images: List<Images>
)

enum class Type {
    @Json(name = "image")
    Image,

    @Json(name = "carousel")
    Carousel,

    @Json(name = "slider")
    Slider,
}