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
import com.jogigo.advertisementapp.utils.AppPreferences

interface FavouriteListener {
    fun onClick(property: Property)
}

class FavouriteAdapter(
    private val context: Context,
    private val favouriteIds: MutableSet<String>,
    private val allProperties: List<Property>,
    private val listener: FavouriteListener,
    private val appPreferences: AppPreferences
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
            val favouriteDate = appPreferences.getFavouriteDate(item)
            if (item.favourite &&  favouriteDate != null) {
                dataAdded.visibility = VISIBLE
                dataAdded.text = favouriteDate
            } else {
                dataAdded.visibility = GONE
                dataAdded.text = ""
            }

            starImageView.setColorFilter(context.getColor(R.color.greenIdealista))

            itemView.setOnClickListener { listener.onClick(item) }

            val airConditioningLayout =
                itemView.findViewById<LinearLayout>(R.id.air_conditioning_layout)
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

}