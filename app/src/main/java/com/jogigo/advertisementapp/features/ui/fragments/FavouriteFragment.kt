package com.jogigo.advertisementapp.features.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.FragmentFavouriteBinding
import com.jogigo.advertisementapp.features.adapters.FavouriteAdapter
import com.jogigo.advertisementapp.features.adapters.FavouriteListener
import com.jogigo.advertisementapp.features.ui.activities.DetailActivity
import com.jogigo.advertisementapp.utils.AppPreferences

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private var favouriteAdapter: FavouriteAdapter? = null
    private val housingViewModel: HousingViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        housingViewModel.loadFavourites(requireContext())
        setUpObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObserver() {
        housingViewModel.favourites.observe(viewLifecycleOwner) { favourites ->
            val appPreferences = AppPreferences(requireContext())
            val allProperties = appPreferences.properties
            val favouritesSet = favourites.keys.toMutableSet()
            if (favourites.isNotEmpty() && allProperties.isNotEmpty()) {
                favouriteAdapter = FavouriteAdapter(
                    requireContext(),
                    favouritesSet,
                    allProperties,
                    object : FavouriteListener {
                        override fun onClick(property: Property) {
                            DetailActivity.open(requireContext())
                        }
                    }
                )

                binding.favourites.adapter = favouriteAdapter
                binding.nodata.visibility = GONE
            } else {
                favouriteAdapter?.clear()

                binding.nodata.visibility = VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        housingViewModel.loadFavourites(requireContext())
        setUpObserver()
    }
}

