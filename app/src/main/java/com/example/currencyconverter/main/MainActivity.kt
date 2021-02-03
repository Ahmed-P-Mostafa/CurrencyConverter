package com.example.currencyconverter.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {
            val fromAMount = binding.etAmount.text.toString()
            val fromCurrency = binding.spFromCurrency.selectedItem.toString()
            val toCurrency = binding.spToCurrency.selectedItem.toString()
            viewModel.convert(amount = fromAMount,fromCurrency = fromCurrency,toCurrency = toCurrency)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.conversion.collect {
                when(it){
                    is MainViewModel.CurrencyEvent.Success->{
                        binding.progressBar.visibility = View.GONE
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.setText(it.resultText)
                    }
                    is MainViewModel.CurrencyEvent.Failure->{
                        binding.progressBar.visibility = View.GONE
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.setText(it.errorText)

                    }
                    is MainViewModel.CurrencyEvent.Loading->{
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    else-> Unit
                }
            }
        }

    }
}