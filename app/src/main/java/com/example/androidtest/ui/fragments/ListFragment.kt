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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

        callAPI()

        initObserver()

        setPullToRefreshListener()
    }

    /**
     * Method to get callback of pull to refresh
     *
     * */
    private fun setPullToRefreshListener() {
        binding.simpleSwipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                callAPI()
            }

        })
    }

    /*
     * Used to get a get the response data via observer
     * */
    private fun initObserver() {
        val rows: MutableList<DataModelItem>? = ArrayList()
        viewModel.mutableLiveData.observe(this, Observer { apiResponse ->
            if (apiResponse != null) {
                if (!TextUtils.isEmpty(apiResponse.title)) {
                    binding.toolbar.setTitle(apiResponse.title)
                }
                setRowdataList(apiResponse, rows)

                binding.simpleSwipeRefreshLayout.setRefreshing(false)
                binding.progressBar.visibility = View.GONE
                binding.progressBarLayout.visibility = View.GONE

                setupRecyclerView(rows)
            }
        })
    }

    /*
    * Used to get a formatted list of row data
    * @param apiResponse
    * @param rows
    * */
    private fun setRowdataList(
        apiResponse: APIResponse,
        rows: MutableList<DataModelItem>?
    ) {
        if (apiResponse.rows != null && apiResponse.rows?.size!! > 0) {
            for (row in apiResponse.rows!!) {
                var description: String? = null
                var title: String? = null
                var imageURL: String? = null

                if (row?.title != null && !TextUtils.isEmpty(row.title)) {
                    title = row.title
                }
                if (row?.description != null && !TextUtils.isEmpty(row.description)) {
                    description = row.description
                } else if (row?.description == null && row?.title != null && row.imageHref != null) {
                    description = context?.resources?.getString(R.string.content_not_available)
                }
                if (row?.imageHref != null && !TextUtils.isEmpty(row.imageHref)) {
                    imageURL = row.imageHref
                }

                if (title == null && description == null && imageURL == null) {
                    continue
                } else {
                    rows?.add(DataModelItem(title, description, imageURL))
                }

            }
        }
    }

    /*
    * Used to initialise the views
    * */
    private fun initView() {
        recyclerView = binding.recyclerView
        binding.toolbar.title = getString(R.string.android_test)


    }
    /*
    * method to make API call
    * */

    private fun callAPI() {

        val mContext = context
        if (NetworkUtility.isNetworkAvailable(mContext!!)) {
            viewModel.init()
        } else {
            Toast.makeText(mContext, getString(R.string.Internet_alert), Toast.LENGTH_SHORT).show()
            binding.simpleSwipeRefreshLayout.setRefreshing(false)
        }
    }

    /**
     * method to set the recycler view
     * @param rowList
     * */
    private fun setupRecyclerView(rowList: MutableList<DataModelItem>?) {
        var recyclerviewAdapter: RecyclerviewAdapter? = null

        if (recyclerviewAdapter == null) {
            recyclerView?.layoutManager = (LinearLayoutManager(context))
            recyclerviewAdapter = RecyclerviewAdapter(rowList)
            recyclerView?.adapter = recyclerviewAdapter

        } else {
            recyclerviewAdapter.notifyDataSetChanged()
        }
    }


}



