package com.sumere.kotlincountries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.sumere.kotlincountries.R
import com.sumere.kotlincountries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this@MainActivity.layoutInflater)
        setContentView(binding.root)
    }
}