package com.sumere.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sumere.kotlincountries.R
import com.sumere.kotlincountries.databinding.FragmentCountryBinding
import com.sumere.kotlincountries.util.downloadFromURL
import com.sumere.kotlincountries.util.placeholderProgressBar
import com.sumere.kotlincountries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private lateinit var viewModel: CountryViewModel
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCountryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        arguments?.let {
            id = CountryFragmentArgs.fromBundle(it).countryUUID
        }
        viewModel.getDataFromRoom(id)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryCurrency.text = it.countryCurrency
                binding.countryLanguage.text = it.countryLanguage
                binding.countryRegion.text = it.countryRegion
                context?.let {myContext ->
                    binding.countryImage.downloadFromURL(it.imageURL, placeholderProgressBar(myContext))
                }

            }
        })
    }

}