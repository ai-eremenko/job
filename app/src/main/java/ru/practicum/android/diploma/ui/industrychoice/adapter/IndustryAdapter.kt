package ru.practicum.android.diploma.ui.industrychoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class IndustryAdapter(
    private var items: List<IndustryItemUi>,
    private val onClick: (IndustryItemUi) -> Unit
) : RecyclerView.Adapter<IndustryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.industry_item, parent, false)
        return IndustryViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<IndustryItemUi>) {
        items = newItems
        notifyDataSetChanged()
    }
}
