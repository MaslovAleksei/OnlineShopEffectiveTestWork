package com.margarin.onlineshopeffectivetestwork.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.margarin.onlineshopeffectivetestwork.databinding.CharacteristicsItemBinding

class DetailsAdapter : ListAdapter<Pair<String, String>, DetailsHolder>(DetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder {

        val binding = CharacteristicsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailsHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvInfoTitle.text = item.first
        holder.binding.tvInfoValue.text = item.second
    }
}