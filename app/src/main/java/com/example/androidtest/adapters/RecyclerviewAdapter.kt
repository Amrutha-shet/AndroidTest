package com.example.androidtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.RecyclerviewItemBinding
import com.example.androidtest.model.dataModel.DataModelItem
import com.example.androidtest.viewmodels.BaseViewModel


class RecyclerviewAdapter(var apiResponse: MutableList<DataModelItem>? = null) :
    RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewholder {


        val inflater = LayoutInflater.from(parent?.context)
        val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        var viewholder = RecyclerViewholder(binding)

        return viewholder


    }

    override fun onBindViewHolder(holder: RecyclerViewholder, position: Int) {
        if (apiResponse != null && apiResponse!!.size > 0 ) {
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

        fun bind(
            rowData: DataModelItem

        ) {
            binding?.datamodel = rowData

            binding?.executePendingBindings()

        }

    }
}