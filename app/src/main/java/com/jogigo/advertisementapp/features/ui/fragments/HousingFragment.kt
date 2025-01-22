package com.jogigo.advertisementapp.features.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.databinding.FragmentHousingBinding

class HousingFragment : Fragment() {
    private lateinit var binding: FragmentHousingBinding

    companion object {
        @JvmStatic fun newInstance() = HousingFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_housing, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}