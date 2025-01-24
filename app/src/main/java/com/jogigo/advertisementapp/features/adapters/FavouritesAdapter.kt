package com.jogigo.advertisementapp.features.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.ItemPropertyBinding

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
            ItemPropertyBinding.inflate(
                LayoutInflater.from(context), parent, false
            ).root
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val property = favouriteProperties[position]

        holder.bind(property, listener)
    }

    override fun getItemCount(): Int = favouriteProperties.size

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Property, listener: FavouriteListener) {
            itemView.findViewById<TextView>(R.id.advertisement_title).text = item.address
            itemView.findViewById<TextView>(R.id.advertisement_description).text = item.description
            updateStarColor(itemView.findViewById(R.id.star), item.favourite)
            itemView.setOnClickListener {
                listener.onClick(item)
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