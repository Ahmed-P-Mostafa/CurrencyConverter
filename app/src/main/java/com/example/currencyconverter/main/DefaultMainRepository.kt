package com.example.currencyconverter.main

import android.util.Log
import com.example.currencyconverter.currencyAPI.WebService
import com.example.currencyconverter.data.model.CurrencyResponse
import com.example.currencyconverter.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(private val api:WebService):MainRepository{
    private val TAG = "DefaultMainRepository"
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {

                val response = api.getRates(base = base)
                val result = response.body()
                if (response.isSuccessful && result!=null){
                    Resource.Success(result)
                }else{
                    Resource.Error(response.message())
                }


        }catch (e:Exception){
            Resource.Error(e.localizedMessage?:"Error Occurred")
        }
    }
}