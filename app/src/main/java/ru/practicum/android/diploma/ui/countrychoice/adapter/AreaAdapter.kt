package ru.practicum.android.diploma.ui.countrychoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class AreaAdapter(
    private var items: List<AreaItemUi> = emptyList(),
    private val onClick: (AreaItemUi) -> Unit
) : RecyclerView.Adapter<AreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.area_item, parent, false)
        return AreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<AreaItemUi>) {
        items = newItems
        notifyDataSetChanged()
    }
}
