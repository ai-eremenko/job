package ru.practicum.android.diploma.ui.industrychoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustryAdapter(
    private val onClick: (IndustryItemUi) -> Unit
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private var items: MutableList<IndustryItemUi> = mutableListOf()
    private val originalList: MutableList<IndustryItemUi> = mutableListOf()
    private val filteredList: MutableList<IndustryItemUi> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = IndustryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: MutableList<IndustryItemUi>) {
        items = newItems
        notifyDataSetChanged()
        originalList.clear()
        originalList.addAll(newItems)
    }

    private fun updateDisplayList(updatedList: List<IndustryItemUi>) {
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
                if (item.title.contains(query, ignoreCase = true)) {
                    filteredList.add(item)
                }
            }
            updateDisplayList(filteredList)
        }
    }
}
