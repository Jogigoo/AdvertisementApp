package com.jogigo.advertisementapp.features.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentsViewPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle, var fragments : List<Fragment>) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}
