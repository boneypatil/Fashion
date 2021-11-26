package com.cool.myfashion.utils

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Created by rahul,p
 *
 */
infix fun View?.show(show: Boolean) {
    this?.visibility = if (show) View.VISIBLE else View.GONE
}
infix fun Context?.toast(message: CharSequence?) = message?.let {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}