package com.example.currencyconverter.di

import com.example.currencyconverter.currencyAPI.WebService
import com.example.currencyconverter.main.DefaultMainRepository
import com.example.currencyconverter.main.MainRepository
import com.example.currencyconverter.util.DispatcherProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    private const val BASE_URL= "https://api.exchangeratesapi.io/"

    @Provides
    @Singleton
    fun provideRetrofitSingleton(): WebService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)

    }
    @Provides
    @Singleton
    fun provideMainRepository(api:WebService):MainRepository{
        return DefaultMainRepository(api)
    }

    @Singleton
    @Provides
    fun provideDispatchers():DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }


}