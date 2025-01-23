package com.jogigo.advertisementapp.features.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.api.APIAdvertisement
import com.jogigo.advertisementapp.data.api.APIAdvertisement.Companion.getProperties
import com.jogigo.advertisementapp.data.listeners.BasicListener
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.FragmentHousingBinding
import com.jogigo.advertisementapp.features.adapters.AdvertisementAdapter
import com.jogigo.advertisementapp.features.adapters.PropertyListener
import com.jogigo.advertisementapp.utils.AppPreferences
import com.jogigo.advertisementapp.utils.Extensions

class HousingFragment : Fragment() {
    private lateinit var binding: FragmentHousingBinding
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
        loadData()
    }

    private fun loadData() {
        Extensions.showLoading(binding.loading)
        getProperties(requireContext(), object : BasicListener {
            override fun onOk() {
                binding.loading.visibility = View.GONE
                fillData()
            }

            override fun onError(error: String) {
                binding.loading.visibility = View.GONE
                showErrorDialog(error)
            }
        })
    }

    private fun fillData() {
        if (AppPreferences(requireContext()).properties.isEmpty()) {
            binding.nodata.visibility = View.VISIBLE
        } else {
            advertisementAdapter = AdvertisementAdapter(
                requireContext(), AppPreferences(requireContext()).properties,
                object : PropertyListener {
                    override fun onClick(property: Property) {
                        // open detail
                    }
                },
            )
            binding.advertisements.adapter = advertisementAdapter
        }

    }

    private fun showErrorDialog(errorMessage: String) {

        AlertDialog.Builder(requireContext())
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                binding.loading.visibility = View.GONE
                dialog?.dismiss()
            }
            .setCancelable(true)
            .show()

    }

}