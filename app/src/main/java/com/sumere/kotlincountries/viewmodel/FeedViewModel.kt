package com.sumere.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sumere.kotlincountries.model.Country
import com.sumere.kotlincountries.service.CountryAPIService
import com.sumere.kotlincountries.service.CountryDatabase
import com.sumere.kotlincountries.util.CustomSharedPreferences
import com.sumere.kotlincountries.view.FeedFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application) {

    private var customPreferences = CustomSharedPreferences(getApplication())
    private val countryApiService = CountryAPIService()
    private val compositeDisposable = CompositeDisposable()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
            getDataFromSQLite()
        else{
            getDataFromAPI()
        }
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value = true
        compositeDisposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(countryList: List<Country>){
            countryLoading.value = true
            launch {
                val dao = CountryDatabase(getApplication()).countryDao()
                dao.deleteAllCountries()
                val listLong = dao.insertAll(*countryList.toTypedArray()) // Array to individual
                var i = 0
                while(i < listLong.size){
                    countryList[i].id = listLong[i].toInt()
                    i += 1
                }
                showCountries(countryList)
                Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
            }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}