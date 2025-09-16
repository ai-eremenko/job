package ru.practicum.android.diploma.ui.countrychoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding
import ru.practicum.android.diploma.domain.areas.models.Area

class AreaAdapter(
    private val onClick: (Area) -> Unit
) : RecyclerView.Adapter<AreaViewHolder>() {

    private var items: MutableList<Area> = mutableListOf()
    private val originalList: MutableList<Area> = mutableListOf()
    private val filteredList: MutableList<Area> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val binding = AreaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AreaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: MutableList<Area>) {
        items = newItems
        notifyDataSetChanged()
        originalList.clear()
        originalList.addAll(newItems)
    }

    private fun updateDisplayList(updatedList: List<Area>) {
        items.clear()
        items.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun filter(query: String?) {
        filteredList.clear()
        if (query.isNullOrEmpty()) {
            updateDisplayList(originalList)
        } else {
            for (item in originalList) {
                if (item.name.contains(query, ignoreCase = true)) {
                    filteredList.add(item)
                }
            }
            updateDisplayList(filteredList)
        }
    }
}
