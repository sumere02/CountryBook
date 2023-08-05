package com.sumere.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sumere.kotlincountries.R
import com.sumere.kotlincountries.databinding.ItemCountryBinding
import com.sumere.kotlincountries.model.Country
import com.sumere.kotlincountries.util.downloadFromURL
import com.sumere.kotlincountries.util.placeholderProgressBar
import com.sumere.kotlincountries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private lateinit var binding: ItemCountryBinding

    class CountryViewHolder(val binding: ItemCountryBinding): ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion
        holder.binding.imageView.downloadFromURL(countryList[position].imageURL,
            placeholderProgressBar(holder.binding.root.context)
        )
        holder.binding.root.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}