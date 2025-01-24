package com.jogigo.advertisementapp.features.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.ItemPropertyFullBinding
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
            ItemPropertyFullBinding.inflate(
                LayoutInflater.from(context), parent, false
            ).root
        )

    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) =
        holder.bind(context, items[position], listener, position)

    override fun getItemCount(): Int = items.size

    class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bind(context: Context, item: Property, listener: PropertyListener, position: Int) {

            val dataAdded = itemView.findViewById<TextView>(R.id.data_added)
            val starImageView = itemView.findViewById<ImageView>(R.id.star_icon)
            updateStarColor(starImageView, item.favourite)
            itemView.findViewById<TextView>(R.id.property_address).text = item.address
            if (item.operation == "sale") {
                itemView.findViewById<TextView>(R.id.property_price).text =
                    context.getString(R.string.price_property, item.price)
            } else {
                itemView.findViewById<TextView>(R.id.property_price).text =
                    context.getString(R.string.price_property_rent, item.priceInfo.price.amount)
            }

            itemView.findViewById<TextView>(R.id.property_details).text =
                context.getString(R.string.property_rooms, item.rooms, item.size)
            itemView.findViewById<TextView>(R.id.property_description).text = item.description
            val thumbnailImageView = itemView.findViewById<ImageView>(R.id.property_thumbnail)
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .into(thumbnailImageView)
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

            val airConditioningLayout =
                itemView.findViewById<LinearLayout>(R.id.air_conditioning_layout)

            itemView.findViewById<TextView>(R.id.air_conditioning_label)

            if (item.features.hasAirConditioning) {
                airConditioningLayout.visibility = VISIBLE

            } else {
                airConditioningLayout.visibility = GONE
            }


            val swimmingPoolLayout = itemView.findViewById<LinearLayout>(R.id.swimming_pool_layout)


            if (item.features.hasSwimmingPool == true) {
                swimmingPoolLayout.visibility = VISIBLE

            } else {
                swimmingPoolLayout.visibility = GONE
            }


            val terraceLayout = itemView.findViewById<LinearLayout>(R.id.terrace_layout)
            itemView.findViewById<ImageView>(R.id.terrace_icon)


            if (item.features.hasTerrace == true) {
                terraceLayout.visibility = VISIBLE
            } else {
                terraceLayout.visibility = GONE
            }


            val boxRoomLayout = itemView.findViewById<LinearLayout>(R.id.box_room_layout)


            if (item.features.hasBoxRoom) {
                boxRoomLayout.visibility = VISIBLE
            } else {
                boxRoomLayout.visibility = GONE
            }


            val gardenLayout = itemView.findViewById<LinearLayout>(R.id.garden_layout)


            if (item.features.hasGarden == true) {
                gardenLayout.visibility = VISIBLE
            } else {
                gardenLayout.visibility = GONE
            }
        }

        private fun updateStarColor(starImageView: ImageView, isFavourite: Boolean) {
            val color = if (isFavourite) {
                ContextCompat.getColor(starImageView.context, R.color.greenIdealista)
            } else {
                ContextCompat.getColor(starImageView.context, R.color.grayDisabled)
            }
            starImageView.setColorFilter(color)
        }

        private fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }


}

