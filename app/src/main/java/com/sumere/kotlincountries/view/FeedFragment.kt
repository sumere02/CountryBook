package com.sumere.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumere.kotlincountries.R
import com.sumere.kotlincountries.adapter.CountryAdapter
import com.sumere.kotlincountries.databinding.FragmentFeedBinding
import com.sumere.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        binding.countryList.layoutManager = LinearLayoutManager(context)
        countryAdapter = CountryAdapter(arrayListOf())
        binding.countryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            binding.countryError.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFromAPI()
        }

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner,Observer{ countries ->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner,Observer{error ->
            error?.let {
                if(it) {
                    binding.countryError.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                }else
                    binding.countryError.visibility = View.GONE
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner,Observer{loading->
            loading?.let {
                if(it) {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                }else {
                    binding.countryLoading.visibility = View.GONE
                }
            }

        })
    }


}