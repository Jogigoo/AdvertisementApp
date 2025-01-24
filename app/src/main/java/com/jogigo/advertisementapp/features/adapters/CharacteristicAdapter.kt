package com.jogigo.advertisementapp.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jogigo.advertisementapp.data.models.Characteristic
import com.jogigo.advertisementapp.databinding.ItemCharacteristicBinding


class CharacteristicsAdapter : ListAdapter<Characteristic, CharacteristicsAdapter.CharacteristicViewHolder>(YourDiffCallback()) {

    inner class CharacteristicViewHolder(private val binding: ItemCharacteristicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Characteristic) {
            binding.ivCharacteristicIcon.setImageResource(item.iconResId)
            binding.tvCharacteristicName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacteristicBinding.inflate(inflater, parent, false)
        return CharacteristicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacteristicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size
    class YourDiffCallback : DiffUtil.ItemCallback<Characteristic>() {
        override fun areItemsTheSame(oldItem: Characteristic, newItem: Characteristic): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Characteristic, newItem: Characteristic): Boolean {
            return oldItem == newItem
        }
    }
}