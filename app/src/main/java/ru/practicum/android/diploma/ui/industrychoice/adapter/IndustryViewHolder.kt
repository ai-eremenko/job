package ru.practicum.android.diploma.ui.industrychoice.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.practicum.android.diploma.R

class IndustryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.industry_text)
    private val radio: MaterialRadioButton = itemView.findViewById(R.id.industryButton)

    fun bind(item: IndustryItemUi, onClick: (IndustryItemUi) -> Unit) {
        title.text = item.title
        radio.isChecked = item.isSelected

        itemView.setOnClickListener { onClick(item) }
        radio.setOnClickListener { onClick(item) }
    }
}
