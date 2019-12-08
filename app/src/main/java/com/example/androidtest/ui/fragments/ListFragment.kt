package com.example.androidtest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest.R

import com.example.androidtest.databinding.ListFragmentBinding
import com.example.androidtest.viewmodels.ListViewModel
import com.example.androidtest.adapters.RecyclerviewAdapter
import com.example.androidtest.model.dataModel.DataModelItem
import com.example.androidtest.utils.networkutils.NetworkUtility


class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding

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
        viewModel =  ViewModelProvider(viewModelStore, ViewModelProvider.
            AndroidViewModelFactory.getInstance(activity?.application!!)).get(ListViewModel::class.java)
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
        viewModel.getResponseList().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.simpleSwipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                binding.simpleSwipeRefreshLayout.visibility = View.VISIBLE
                setupRecyclerView(it)
            }
        })
        viewModel.getTitle().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.toolbar.title = it
            }
        })
        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        })
    }

    /*
    * Used to initialise the views
    * */
    private fun initView() {
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
        if (binding.recyclerView?.adapter == null) {
            binding.recyclerView?.layoutManager = (LinearLayoutManager(context))
            binding.recyclerView?.adapter = RecyclerviewAdapter(rowList)

        } else {
            (binding.recyclerView?.adapter as RecyclerviewAdapter).notifyDataSetChanged()
        }
    }
}



