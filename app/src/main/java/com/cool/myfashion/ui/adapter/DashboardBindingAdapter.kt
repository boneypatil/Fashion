package com.cool.myfashion.ui.adapter

import android.widget.ImageView

import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cool.myfashion.R

/**
 * Created by rahul,p
 *
 */
//Binding adapter used to display images from URL using Glide
@BindingAdapter("imageUrl")
fun bindImage(
    imageView: ImageView,
    imgUrl: String?
) {
    Glide.with(imageView.context)
        .load("$imgUrl")
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imageView)
}




