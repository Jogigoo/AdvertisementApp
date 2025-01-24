package com.jogigo.advertisementapp.features.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.databinding.ActivityDetailBinding
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
        viewModel.loading.observe(this) { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        viewModel.propertyDetail.observe(this) { detail ->
            if (detail != null) {
                binding.propertyTitle.text = detail.propertyType
                binding.propertyDescription.text = detail.propertyComment
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
}
