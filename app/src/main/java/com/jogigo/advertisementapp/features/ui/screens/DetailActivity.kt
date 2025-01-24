package com.jogigo.advertisementapp.features.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Characteristic
import com.jogigo.advertisementapp.data.models.toMap
import com.jogigo.advertisementapp.databinding.ActivityDetailBinding
import com.jogigo.advertisementapp.features.adapters.CharacteristicsAdapter
import com.jogigo.advertisementapp.features.adapters.ImageSliderAdapter
import com.jogigo.advertisementapp.features.ui.screens.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, DetailActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.apply {
            setOnClickListener {
                finish()
            }
        }

        val characteristicsAdapter = CharacteristicsAdapter()
        binding.propertyCharacteristics.apply {
            adapter = characteristicsAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }
        viewModel.loading.observe(this) { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.propertyDetail.observe(this) { detail ->
            if (detail != null) {
                binding.propertyDescription.text = detail.propertyComment
                val listUrlImages: MutableList<String> = mutableListOf()
                detail.multimedia.images.forEach { listUrlImages.add(it.url) }
                val imageSlider: ViewPager2 = findViewById(R.id.imageSlider)
                val sliderAdapter = ImageSliderAdapter(listUrlImages)
                imageSlider.adapter = sliderAdapter

                val sliderIndicator: TabLayout = findViewById(R.id.sliderIndicator)
                TabLayoutMediator(sliderIndicator, imageSlider) { _, _ -> }.attach()
                val characteristicsList =
                    mapCharacteristics( detail.moreCharacteristics.toMap())
                characteristicsAdapter.submitList(characteristicsList)
                binding.viewInMapButton.setOnClickListener {
                    val latitude = detail.ubication.latitude
                    val longitude = detail.ubication.longitude
                    val uri = Uri.parse("geo:$detail.,$longitude?q=$latitude,$longitude")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(intent)
                }
                binding.contactButton.setOnClickListener {
                    val phoneNumber = "123456789"
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                }
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showErrorDialog(errorMessage)
            }
        }

        viewModel.getPropertyDetail(this)
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }
    @SuppressLint("StringFormatMatches")
    fun mapCharacteristics(moreCharacteristics: Map<String, Any?>?): List<Characteristic> {
        if (moreCharacteristics == null) return emptyList()

        return listOf(
            Characteristic(
                iconResId = R.drawable.euro,
                name = getString(
                    R.string.community_costs,
                    moreCharacteristics["communityCosts"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.bed,
                name = getString(
                    R.string.room_number,
                    moreCharacteristics["roomNumber"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.wc,
                name = getString(
                    R.string.bath_number,
                    moreCharacteristics["bathNumber"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.apartment,
                name = if (moreCharacteristics["exterior"] as? Boolean == true) getString(R.string.exterior) else getString(
                    R.string.interior
                )
            ),
            Characteristic(
                iconResId = R.drawable.table_bar,
                name = getString(
                    R.string.housing_furnitures,
                    moreCharacteristics["housingFurnitures"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.account,
                name = if (moreCharacteristics["agencyIsABank"] as? Boolean == true) getString(R.string.agency_is_a_bank) else getString(
                    R.string.not_a_bank
                )
            ),
            Characteristic(
                iconResId = R.drawable.bolt,
                name = getString(
                    R.string.energy_certification,
                    moreCharacteristics["energyCertificationType"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.floor,
                name = getString(R.string.floor, moreCharacteristics["floor"] ?: "No especificado")
            ),
            Characteristic(
                iconResId = R.drawable.crop,
                name = getString(
                    R.string.constructed_area,
                    moreCharacteristics["constructedArea"] ?: "No especificado"
                )
            ),
            Characteristic(
                iconResId = R.drawable.elevator,
                name = if (moreCharacteristics["lift"] as? Boolean == true) getString(R.string.with_lift) else getString(
                    R.string.without_lift
                )
            ),
            Characteristic(
                iconResId = R.drawable.trastero,
                name = if (moreCharacteristics["boxroom"] as? Boolean == true) getString(R.string.with_boxroom) else getString(
                    R.string.without_boxroom
                )
            ),
            Characteristic(
                iconResId = R.drawable.apartment,
                name = if (moreCharacteristics["isDuplex"] as? Boolean == true) getString(R.string.is_duplex) else getString(
                    R.string.not_duplex
                )
            ),
            Characteristic(
                iconResId = R.drawable.engineering,
                name = when (moreCharacteristics["status"]) {
                    "renew" -> getString(R.string.reformed)
                    "new" -> getString(R.string.nuevo)
                    else -> getString(R.string.status_not_specified)
                }
            )
        )
    }
}


