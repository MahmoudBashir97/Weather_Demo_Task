package com.mahmoudbashir.weathertask_app.Retrofit

import com.mahmoudbashir.weathertask_app.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestRetrofitInstance{

    companion object{

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        val api: ApiServiceInterface by lazy{
            retrofit.create(ApiServiceInterface::class.java)
        }
    }
}