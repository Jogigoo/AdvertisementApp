package com.jogigo.advertisementapp.features.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.databinding.ActivityMainBinding
import com.jogigo.advertisementapp.features.adapters.MainFragmentsViewPagerAdapter
import com.jogigo.advertisementapp.features.ui.AdvertisementAppActivity
import com.jogigo.advertisementapp.features.ui.fragments.FavouriteFragment
import com.jogigo.advertisementapp.features.ui.fragments.HousingFragment

class MainActivity : AdvertisementAppActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentPage = 0
    private var mFragments = listOf<Fragment>()

    companion object {
        fun open(context: Context) =
            context.startActivity(Intent(context, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        mFragments = listOf(
            HousingFragment.newInstance(),
            FavouriteFragment.newInstance(),
        )
        val adapter = MainFragmentsViewPagerAdapter(supportFragmentManager, lifecycle, mFragments)
        binding.viewpager.adapter = adapter
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position != currentPage) {
                    binding.bottomNavigation.selectedItemId = when (position) {
                        0 -> R.id.item_housing
                        1 -> R.id.item_favourite
                        else -> R.id.item_housing
                    }
                }
            }
        })
        binding.bottomNavigation.setOnItemSelectedListener {
            val newPage = when (it.itemId) {
                R.id.item_housing -> 0
                R.id.item_favourite -> 1
                else -> 0
            }
            if (newPage != currentPage) {
                currentPage = newPage
                binding.viewpager.setCurrentItem(currentPage, true)
                true
            } else {
                false
            }
        }
    }
}