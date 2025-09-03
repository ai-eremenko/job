package ru.practicum.android.diploma.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyPreview

class VacancyListAdapter(
    private var vacancyPreview: List<VacancyPreview>,
    private val onVacancyPreviewClick: (VacancyPreview) -> Unit
    ) : RecyclerView.Adapter<VacancyListViewHolder> () {

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
    }
