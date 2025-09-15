package ru.practicum.android.diploma.ui.countrychoice.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding
import ru.practicum.android.diploma.domain.areas.models.Area

class AreaViewHolder(
    private val binding: AreaItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Area, onClick: (Area) -> Unit) {
        binding.areaText.text = item.name
        // Можно менять иконку, если нужно: item.isSelected

        binding.root.setOnClickListener { onClick(item) }
        binding.areaBtn.setOnClickListener { onClick(item) }
    }
}
