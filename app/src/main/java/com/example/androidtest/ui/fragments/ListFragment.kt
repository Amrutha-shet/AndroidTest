package com.example.androidtest.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R

import com.example.androidtest.databinding.ListFragmentBinding
import com.example.androidtest.model.APIResponse
import com.example.androidtest.viewmodels.ListViewModel
import com.example.androidtest.adapters.RecyclerviewAdapter
import com.example.androidtest.model.dataModel.DataModelItem


class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private var recyclerView: RecyclerView? = null

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        val view: View = binding.root
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        binding.listVM = viewModel
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        getresponse()
    }

    private fun initView() {
        recyclerView = binding.recyclerView


    }

    private fun getresponse() {
        var apiResponse: APIResponse? = null
        var rows: ArrayList<DataModelItem>? = null
        var title: String? = ""
        viewModel.init()

        viewModel.getAPIResponse()?.observe(this, Observer {
            if (it != null) {
                apiResponse = it
                if (!TextUtils.isEmpty(apiResponse?.title)) {
                    title = apiResponse?.title
                }

                if (apiResponse?.rows != null && apiResponse?.rows?.size!! > 0) {
                    for (row in apiResponse?.rows!!) {
                        rows?.add(DataModelItem(row?.title, row?.description, row?.imageHref))
                    }
                }
            }
        })

        setupRecyclerView(rows)
    }

    private fun setupRecyclerView(apiResponse: ArrayList<DataModelItem>?) {
        var recyclerviewAdapter: RecyclerviewAdapter? = null

        if (recyclerviewAdapter == null) {
            recyclerView?.layoutManager = (LinearLayoutManager(context))
            recyclerView?.adapter = RecyclerviewAdapter(apiResponse)

            recyclerView?.adapter = recyclerviewAdapter

        } else {
            recyclerviewAdapter.notifyDataSetChanged()
        }
    }


}



