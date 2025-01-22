package com.jogigo.advertisementapp.features.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.databinding.ActivitySplashBinding
import com.jogigo.advertisementapp.features.ui.AdvertisementAppActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AdvertisementAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            MainActivity.open(this)
            finish()
        },1500L)

    }
}