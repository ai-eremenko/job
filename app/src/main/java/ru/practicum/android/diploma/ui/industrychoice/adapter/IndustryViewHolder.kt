package ru.practicum.android.diploma.ui.industrychoice.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustryViewHolder(
    private val binding: IndustryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: IndustryItemUi, onClick: (IndustryItemUi) -> Unit) {
        binding.industryText.text = item.title
        binding.industryButton.isChecked = item.isSelected

        binding.root.setOnClickListener { onClick(item) }
        binding.industryButton.setOnClickListener { onClick(item) }
    }
}
