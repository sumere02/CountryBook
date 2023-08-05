package com.sumere.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sumere.kotlincountries.model.Country

@Dao
interface CountryDao {

    //INSERT INTO
    //suspend -> Coroutine, pause & resume
    //vararg -> multiple country objects
    // List<Long> -> return primary keys
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE id = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}