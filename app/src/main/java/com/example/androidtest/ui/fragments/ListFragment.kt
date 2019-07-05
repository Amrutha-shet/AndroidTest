package com.example.androidtest.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.androidtest.utils.networkutils.NetworkUtility


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

        callAPI()

        initObserver()
    }

    private fun initObserver() {
        var apiResponse: APIResponse? = null
        var rows: MutableList<DataModelItem>? = ArrayList()
        var title: String? = ""
        viewModel.mutableLiveData.observe(this, Observer {
            if (it != null) {
                apiResponse = it
                if (!TextUtils.isEmpty(apiResponse?.title)) {
                    title = apiResponse?.title
                    viewModel.title.postValue(apiResponse?.title)
                    Log.d("Response Arrived", apiResponse?.title)
                    binding
                }

                if (apiResponse?.rows != null && apiResponse?.rows?.size!! > 0) {
                    for (row in apiResponse?.rows!!) {

                        rows?.add(DataModelItem(row?.title, row?.description, row?.imageHref))
                    }
                }
                setupRecyclerView(rows)
            }
        })
    }

    private fun initView() {
        recyclerView = binding.recyclerView


    }

    private fun callAPI() {

        Handler().postDelayed(object : Runnable{
            override fun run() {
                val mContext = context
                if(NetworkUtility.isNetworkAvailable(mContext!!)) {
                    viewModel.init()
                } else {
                    Toast.makeText(mContext,"Please ckeck your internet connection", Toast.LENGTH_SHORT)
                }
            }

        },2000L

        )



    }

    private fun setupRecyclerView(rowList: MutableList<DataModelItem>?) {
        var recyclerviewAdapter: RecyclerviewAdapter? = null

        if (recyclerviewAdapter == null) {
            recyclerView?.layoutManager = (LinearLayoutManager(context))
            recyclerviewAdapter =  RecyclerviewAdapter(rowList)
            recyclerView?.adapter = recyclerviewAdapter

        } else {
            recyclerviewAdapter.notifyDataSetChanged()
        }
    }


}


