package com.jogigo.advertisementapp.features.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.jogigo.advertisementapp.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.FragmentHousingBinding
import com.jogigo.advertisementapp.features.adapters.AdvertisementAdapter
import com.jogigo.advertisementapp.features.adapters.PropertyListener
import com.jogigo.advertisementapp.features.ui.activities.DetailActivity

class HousingFragment : Fragment() {
    private lateinit var binding: FragmentHousingBinding
    private val viewModel: HousingViewModel by activityViewModels()
    private var advertisementAdapter: AdvertisementAdapter? = null


    companion object {
        @JvmStatic
        fun newInstance() = HousingFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_housing, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.loadProperties(requireContext())
    }

    private fun setupObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.properties.observe(viewLifecycleOwner) { properties ->
            if (properties.isNullOrEmpty()) {
                binding.nodata.visibility = View.VISIBLE
            } else {
                binding.nodata.visibility = View.GONE
                advertisementAdapter = AdvertisementAdapter(
                    requireContext(),
                    properties.toMutableList(),
                    object : PropertyListener {
                        override fun onClick(property: Property) {
                            DetailActivity.open(requireContext())
                        }

                        override fun onAddFavourite(property: Property) {
                            viewModel.addFavourite(requireContext(), property)
                        }

                        override fun onDeleteFavourite(property: Property) {
                            viewModel.removeFavourite(requireContext(), property)
                        }

                        @SuppressLint("NotifyDataSetChanged")
                        override fun onItemUpdated(position: Int) {
                            advertisementAdapter?.notifyDataSetChanged()
                        }
                    }
                )
                binding.advertisements.adapter = advertisementAdapter
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showErrorDialog(errorMessage)
            }
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }
}