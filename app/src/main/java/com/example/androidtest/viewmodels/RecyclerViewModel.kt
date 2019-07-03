package com.example.androidtest.viewmodels

import androidx.lifecycle.ViewModel
import android.R
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

import androidx.databinding.BindingAdapter



class RecyclerViewModel(var imagePath:String  = "") : ViewModel() {

    fun getImageUrl(): String {

        return imagePath
    }

    @BindingAdapter("bind:imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Log.d("Image URL", imageUrl)
       Picasso.get()
           .load(imageUrl)
           .into(view)
    }
}