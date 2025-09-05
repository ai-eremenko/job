package ru.practicum.android.diploma.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Dimensions
import ru.practicum.android.diploma.util.toPx

class VacancyListViewHolder(
    private val binding: VacancyItemBinding,
    private val onClick: (VacancyPreviewPresent) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: VacancyPreviewPresent) {
        binding.root.setOnClickListener {
            onClick(model)
        }
        binding.name.text = "${model.name}, ${model.addressCity}"
        binding.employerName.text = model.employerName
        binding.salary.text = model.salary

        Glide.with(binding.root)
            .load(model.url)
            .placeholder(R.drawable.placeholder_vacancy_preview)
            .transform(RoundedCorners(itemView.context.toPx(Dimensions.CORNER_RADIUS_12)))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.sourceImage)
    }
}
