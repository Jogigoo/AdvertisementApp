package com.jogigo.advertisementapp.features.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.ItemPropertyBinding
import com.jogigo.advertisementapp.utils.Extensions.Companion.getCurrentDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface PropertyListener {
    fun onClick(property: Property)
    fun onAddFavourite(property: Property)
    fun onDeleteFavourite(property: Property)
    fun onItemUpdated(position: Int)
}

class AdvertisementAdapter(
    private val context: Context,
    private val items: MutableList<Property>,
    private val listener: PropertyListener
) : RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder =
        AdvertisementViewHolder(
            ItemPropertyBinding.inflate(
                LayoutInflater.from(context), parent, false
            ).root
        )

    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) =
        holder.bind(items[position], listener, position)

    override fun getItemCount(): Int = items.size

    class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Property, listener: PropertyListener, position: Int) {
            itemView.findViewById<TextView>(R.id.advertisement_title).text = item.address
            itemView.findViewById<TextView>(R.id.advertisement_description).text = item.description
            val dataAdded = itemView.findViewById<TextView>(R.id.data_added)
            val starImageView = itemView.findViewById<ImageView>(R.id.star)
            updateStarColor(starImageView, item.favourite)
            if (item.favourite && item.dateFavourite?.isNotEmpty() != false) {
                dataAdded.visibility = VISIBLE
                dataAdded.text = item.dateFavourite
            } else {
                dataAdded.visibility = GONE
                dataAdded.text = ""
            }
            starImageView.setOnClickListener {
                item.favourite = !item.favourite

                if (item.favourite) {
                    dataAdded.visibility = VISIBLE
                    val currentDate = getCurrentDateTime()
                    dataAdded.text = currentDate
                    item.dateFavourite = currentDate
                    listener.onAddFavourite(item)
                } else {
                    dataAdded.visibility = GONE
                    dataAdded.text = ""
                    item.dateFavourite = ""
                    listener.onDeleteFavourite(item)
                }
                updateStarColor(starImageView, item.favourite)


                listener.onItemUpdated(position)
            }
            itemView.setOnClickListener { listener.onClick(item) }
        }

        private fun updateStarColor(starImageView: ImageView, isFavourite: Boolean) {
            val color = if (isFavourite) {
                ContextCompat.getColor(starImageView.context, R.color.greenIdealista)
            } else {
                ContextCompat.getColor(starImageView.context, R.color.grayDisabled)
            }
            starImageView.setColorFilter(color)
        }


    }
}