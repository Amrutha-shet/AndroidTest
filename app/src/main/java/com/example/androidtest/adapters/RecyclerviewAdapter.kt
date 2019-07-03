package com.example.androidtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.databinding.RecyclerviewItemBinding
import com.example.androidtest.model.dataModel.DataModelItem


class RecyclerviewAdapter(var apiResponse: ArrayList<DataModelItem>? = null) :
    RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewholder {
        var binding: RecyclerviewItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.recyclerview_item, parent,
                false
            )
        var viewholder = RecyclerViewholder(binding)
        return viewholder


    }

    override fun onBindViewHolder(holder: RecyclerViewholder, position: Int) {
        if (apiResponse != null && apiResponse!!.size > 0) {
            holder.bind(apiResponse!![position])
        }


    }

    override fun getItemCount(): Int {
        if (apiResponse != null && apiResponse!!.size > 0) {
            return apiResponse!!.size
        } else {
            return 0
        }
    }


    class RecyclerViewholder(bindingItem: RecyclerviewItemBinding) : RecyclerView.ViewHolder(bindingItem.root) {
        var binding: RecyclerviewItemBinding? = null

        init {
            binding = bindingItem
        }

        fun bind(rowData: DataModelItem) {
            binding?.datamodel = rowData
            binding?.recycleritemVM?.imagePath = rowData.imageURL!!
            binding?.executePendingBindings()

        }

    }
}