package ru.practicum.android.diploma.ui.regionchoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding
import ru.practicum.android.diploma.domain.areas.models.Area

class RegionAdapter(
    private var items: List<Area>,
    private val onClick: (Area) -> Unit
) : RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    inner class RegionViewHolder(private val binding: AreaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(area: Area) {
            binding.areaText.text = area.name
            binding.root.setOnClickListener { onClick(area) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = AreaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<Area>) {
        items = newItems
        notifyDataSetChanged()
    }
}
