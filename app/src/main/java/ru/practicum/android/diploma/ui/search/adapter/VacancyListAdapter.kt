package ru.practicum.android.diploma.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent

class VacancyListAdapter(
    private val onVacancyPreviewClick: (VacancyPreviewPresent) -> Unit
) : RecyclerView.Adapter<VacancyListViewHolder>() {

    var vacancyPreview: MutableList<VacancyPreviewPresent> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyListViewHolder {
        val binding = VacancyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyListViewHolder(binding, onVacancyPreviewClick)
    }

    override fun onBindViewHolder(holder: VacancyListViewHolder, position: Int) {
        holder.bind(vacancyPreview[position])
    }

    override fun getItemCount() = vacancyPreview.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateVacancies(newVacancies: List<VacancyPreviewPresent>) {
        vacancyPreview = newVacancies
        notifyDataSetChanged()
    }
}
