package com.jogigo.advertisementapp.data.api

import android.content.Context
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.listeners.BasicListener
import com.jogigo.advertisementapp.utils.AppPreferences
import com.jogigo.advertisementapp.utils.Extensions.Companion.isNetworkConnected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class APIAdvertisement : WSBase() {
    companion object {
        fun getProperties(context: Context, listener: BasicListener) {
            if (context.isNetworkConnected()) {
                CoroutineScope(Dispatchers.IO).launch {
                    runCatching {
                        try {
                            val call = getRetrofit(context)
                                .create(ApiService::class.java)
                                .getAdvertisements()
                                .execute()
                            val response = call.body()
                            launch(Dispatchers.Main) {
                                response?.let {
                                    checkStatus(
                                        context,
                                        call.code(),
                                        object : BasicListener {
                                            override fun onOk() {
                                                AppPreferences(context).properties =
                                                    it.toMutableList()
                                                listener.onOk()
                                            }

                                            override fun onError(error: String) {
                                                listener.onError(error)
                                            }
                                        })
                                } ?: run {
                                    listener.onError(context.getString(R.string.error_server))
                                }
                            }
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                listener.onError(e.message.toString())
                            }
                        }
                    }
                }
            } else {
                listener.onError(context.getString(R.string.error_internet))
            }
        }

        fun getPropertyDetail(context: Context, listener: BasicListener) {
            if (context.isNetworkConnected()) {
                CoroutineScope(Dispatchers.IO).launch {
                    runCatching {
                        try {
                            val call = getRetrofit(context)
                                .create(ApiService::class.java)
                                .getAdvertisementDetail()
                                .execute()

                            val response = call.body()
                            launch(Dispatchers.Main) {
                                response?.let {
                                    checkStatus(
                                        context,
                                        call.code(),
                                        object : BasicListener {
                                            override fun onOk() {
                                                listener.onOk()
                                            }

                                            override fun onError(error: String) {
                                                listener.onError(error)
                                            }
                                        })
                                } ?: run {
                                    listener.onError(context.getString(R.string.error_server))
                                }
                            }
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                listener.onError(context.getString(R.string.error_unknown))
                            }
                        }
                    }
                }
            } else {
                listener.onError(context.getString(R.string.error_internet))
            }
        }
    }
}