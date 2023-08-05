package com.sumere.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumere.kotlincountries.model.Country
import com.sumere.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application): BaseViewModel(application) {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(id: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            countryLiveData.value = dao.getCountry(id)
        }
    }
}