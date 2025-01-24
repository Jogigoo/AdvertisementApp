package com.jogigo.advertisementapp.data.api

import android.content.Context

import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.listeners.BasicListener
import com.jogigo.advertisementapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class WSBase {

    companion object {
        fun getRetrofit(context: Context, token: String? = null): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(
                    OkHttpClient
                        .Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder()
                                .build()
                            chain.proceed(newRequest)
                        }
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun checkStatus(context: Context, code: Int) {
            if (code != 200 && code != 201) {
                throw Exception(context.getString(R.string.error_unknown))
            }
        }

    }
}