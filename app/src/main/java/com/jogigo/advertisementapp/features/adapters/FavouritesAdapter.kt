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

interface FavouriteListener {
    fun onClick(property: Property)
}

class FavouriteAdapter(
    private val context: Context,
    private val favouriteIds: MutableSet<String>,
    private val allProperties: List<Property>,
    private val listener: FavouriteListener
) : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val favouriteProperties: MutableList<Property>
        get() = allProperties.filter { it.propertyCode in favouriteIds }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            ItemPropertyFullBinding.inflate(
                LayoutInflater.from(context), parent, false
            ).root
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val property = favouriteProperties[position]

        holder.bind(context, property, listener)
    }

    override fun getItemCount(): Int = favouriteProperties.size

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bind(context: Context, item: Property, listener: FavouriteListener) {

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

            starImageView.setColorFilter(context.getColor(R.color.greenIdealista))

            itemView.setOnClickListener { listener.onClick(item) }

            // Sección de propiedades adicionales
            // Aire acondicionado
            val airConditioningLayout =
                itemView.findViewById<LinearLayout>(R.id.air_conditioning_layout)
            itemView.findViewById<ImageView>(R.id.air_conditioning_icon)
            val airConditioningLabel = itemView.findViewById<TextView>(R.id.air_conditioning_label)

            if (item.features.hasAirConditioning) {
                airConditioningLayout.visibility = VISIBLE
                airConditioningLabel.text = itemView.context.getString(R.string.txt_aire)
            } else {
                airConditioningLayout.visibility = GONE
            }

            // Piscina
            val swimmingPoolLayout = itemView.findViewById<LinearLayout>(R.id.swimming_pool_layout)
            itemView.findViewById<ImageView>(R.id.swimming_pool_icon)
            val swimmingPoolLabel = itemView.findViewById<TextView>(R.id.swimming_pool_label)

            if (item.features.hasSwimmingPool == true) {
                swimmingPoolLayout.visibility = VISIBLE
                swimmingPoolLabel.text = itemView.context.getString(R.string.txt_piscina)
            } else {
                swimmingPoolLayout.visibility = GONE
            }

            // Terraza
            val terraceLayout = itemView.findViewById<LinearLayout>(R.id.terrace_layout)
            itemView.findViewById<ImageView>(R.id.terrace_icon)
            val terraceLabel = itemView.findViewById<TextView>(R.id.terrace_label)

            if (item.features.hasTerrace == true) {
                terraceLayout.visibility = VISIBLE
                terraceLabel.text = itemView.context.getString(R.string.txt_terraza)
            } else {
                terraceLayout.visibility = GONE
            }

            // Trastero
            val boxRoomLayout = itemView.findViewById<LinearLayout>(R.id.box_room_layout)
            itemView.findViewById<ImageView>(R.id.box_room_icon)
            val boxRoomLabel = itemView.findViewById<TextView>(R.id.box_room_label)

            if (item.features.hasBoxRoom) {
                boxRoomLayout.visibility = VISIBLE
                boxRoomLabel.text = itemView.context.getString(R.string.txt_trastero)
            } else {
                boxRoomLayout.visibility = GONE
            }

            // Jardín
            val gardenLayout = itemView.findViewById<LinearLayout>(R.id.garden_layout)
            itemView.findViewById<ImageView>(R.id.garden_icon)
            val gardenLabel = itemView.findViewById<TextView>(R.id.garden_label)

            if (item.features.hasGarden == true) {
                gardenLayout.visibility = VISIBLE
                gardenLabel.text = itemView.context.getString(R.string.txt_garden)
            } else {
                gardenLayout.visibility = GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        favouriteIds.clear()
        notifyDataSetChanged()
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