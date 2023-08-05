package com.sumere.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sumere.kotlincountries.R

//Extension

/*
fun String.myExtension(myParameter: String){
    println(myParameter)
}
*/
fun ImageView.downloadFromURL(url: String?,progressDrawable: CircularProgressDrawable){
    url?.let {
        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(it)
            .into(this)

    }
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}