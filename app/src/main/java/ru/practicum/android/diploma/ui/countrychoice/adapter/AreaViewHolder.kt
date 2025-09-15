package ru.practicum.android.diploma.ui.countrychoice.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class AreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.area_text)
    private val button: ImageView = itemView.findViewById(R.id.area_btn)

    fun bind(item: AreaItemUi, onClick: (AreaItemUi) -> Unit) {
        title.text = item.name
        // Можно использовать isSelected для изменения иконки

        itemView.setOnClickListener { onClick(item) }
        button.setOnClickListener { onClick(item) }
    }
}
