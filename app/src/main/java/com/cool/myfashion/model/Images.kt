package com.cool.myfashion.model

import java.io.Serializable

/**
 * Created by rahul.p
 *
 */
data class Images (

	val url : String,
	val width : Int,
	val height : Int,
	val format : String
):Serializable