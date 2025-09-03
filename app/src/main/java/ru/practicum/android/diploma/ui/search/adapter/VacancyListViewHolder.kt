package ru.practicum.android.diploma.ui.search.adapter

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyPreview
import ru.practicum.android.diploma.util.Dimensions

class VacancyListViewHolder(
    private val binding: VacancyItemBinding,
    private val onClick: (VacancyPreview) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: VacancyPreview) {
        binding.root.setOnClickListener {
            onClick(model)
        }
        binding.name.text = "${model.name}, ${model.addressCity}"
        binding.employerName.text = model.employerName
        binding.salary.text = when {
            model.salaryFrom.isNullOrEmpty().not() && model.salaryTo.isNullOrEmpty().not() ->
                "От ${model.salaryFrom} до  ${model.salaryTo}"
            model.salaryFrom.isNullOrEmpty().not() -> "От ${model.salaryFrom}"
            model.salaryCurrency.isNullOrEmpty().not() -> model.salaryCurrency
            else -> "Зарплата не указана"
        }
        Glide.with(binding.root)
            .load(model.albumCoverUrl)
            .placeholder(R.drawable.placeholder_vacancy_preview)
            .transform(RoundedCorners(itemView.context.toPx(Dimensions.CORNER_RADIUS_12)))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.sourceImage)
    }

    private fun Context.toPx(dp: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
}
