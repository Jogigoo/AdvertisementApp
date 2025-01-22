package com.jogigo.advertisementapp.features.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.databinding.ItemPropertyBinding

interface PropertyListener {
    fun onClick(property: Property)
}
class AdvertisementAdapter(private val context: Context, private val items: MutableList<Property>, private val listener: PropertyListener) : RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder = AdvertisementViewHolder(ItemPropertyBinding.inflate(
        LayoutInflater.from(context), parent, false).root)
    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) = holder.bind(items[position], listener)
    override fun getItemCount(): Int = items.size

    class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Property, listener: PropertyListener) {
            itemView.findViewById<TextView>(R.id.advertisement_title).text = item.address
            itemView.findViewById<TextView>(R.id.advertisement_description).text = item.description
            return itemView.setOnClickListener { listener.onClick(item) }
        }
    }
}