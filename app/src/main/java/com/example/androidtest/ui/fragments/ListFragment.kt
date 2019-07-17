package com.example.androidtest.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
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
import com.example.androidtest.model.Rows
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
        initObserver()
        callAPI()
        setPullToRefreshListener()
    }

    /**
     * Method to get callback of pull to refresh
     *
     * */
    private fun setPullToRefreshListener() {
        binding.simpleSwipeRefreshLayout.setOnRefreshListener { callAPI() }
    }

    /*
     * Used to get a get the response data via observer
     * */
    private fun initObserver() {
        viewModel.mutableLiveData.observe(this, Observer { apiResponse ->
            if (apiResponse != null) {
                if (!TextUtils.isEmpty(apiResponse.title)) {
                    binding.toolbar.title = apiResponse.title
                }
                setRowdataList(apiResponse)
                binding.simpleSwipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                binding.simpleSwipeRefreshLayout.visibility = View.VISIBLE
            }
        })
    }

    /*
    * Used to get a formatted list of row data
    * @param apiResponse
    * @param rows
    * */
    private fun setRowdataList(
        apiResponse: APIResponse
    ) {
        val rows: MutableList<DataModelItem>? = ArrayList()
        when {
            apiResponse.rows != null && apiResponse.rows.isNotEmpty() ->
                loop@ apiResponse.rows.forEach { row ->
                    var description: String? = null
                    var title: String? = null
                    var imageURL: String? = null
                    title = setTitle(row, title)
                    description = setDescription(row, description)
                    imageURL = setImageURL(row, imageURL)
                    when {
                        title == null && description == null && imageURL == null -> return@forEach
                        else -> rows?.add(DataModelItem(title, description, imageURL))
                    }
                }
        }
        setupRecyclerView(rows)
    }

    private fun setImageURL(row: Rows, imageURL: String?): String? {
        var imageURL1 = imageURL
        when {
            row.imageHref != null && !TextUtils.isEmpty(row.imageHref) -> imageURL1 = row.imageHref
        }
        return imageURL1
    }

    private fun setDescription(row: Rows, description: String?): String? {
        var description1 = description
        when {
            row.description != null && !TextUtils.isEmpty(row.description) ->
                description1 = row.description
            row.description == null && row.title != null && row.imageHref != null ->
                description1 = context?.resources?.getString(R.string.content_not_available)
        }
        return description1
    }

    private fun setTitle(row: Rows, title: String?): String? {
        var title1 = title
        when {
            row.title != null && !TextUtils.isEmpty(row.title) -> title1 = row.title
        }
        return title1
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
            binding.simpleSwipeRefreshLayout.isRefreshing = false
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.mutableLiveData.removeObservers(this)//This is done to avoid multiple callback
        // to observer during future fragment transaction
    }
}



