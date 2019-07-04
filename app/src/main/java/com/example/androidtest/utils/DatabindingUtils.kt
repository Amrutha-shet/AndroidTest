package com.example.androidtest.utils

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.androidtest.R


object DataBindingUtils {

        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            imageUrl?.run {
                Log.d("Image URL", imageUrl)
                /*Picasso.with(view.getContext())
                    .load(imageUrl)
                    .resize(100,100)
                    .placeholder(R.drawable.ic_landscape_black_24dp)
                    .into(view)
*/
                Glide
                    .with(view.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape_black_24dp)
                    .into(view);

            }

        }




}