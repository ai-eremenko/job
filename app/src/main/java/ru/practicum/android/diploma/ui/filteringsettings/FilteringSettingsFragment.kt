package ru.practicum.android.diploma.ui.filteringsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.presentation.filteringsettings.FilterViewModel
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController

class FilteringSettingsFragment : Fragment() {

    private var _binding: FragmentFilteringSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)

        setupObserves()
        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.applyButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.resetButton.setOnClickListener {
            viewModel.clearFilter()
        }

        binding.materialCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            viewModel.updateFilterSettings(isChecked)
        }

        // обработка editText
    }

    private fun setupObserves() {
        viewModel.getFilterStateLiveData().observe(viewLifecycleOwner) {
            setupContent(it)
        }
    }

    private fun setupContent(filter: FilterSettings) {
        showContent(filter)
        updateButtonsVisibility(filter.hasActiveFilters())
    }

    private fun showContent(filter: FilterSettings) {
        with(binding) {

            etWorkplace.setText(filter.areaName ?: "")
            arrowForward.setImageResource(if (filter.areaName != null) R.drawable.ic_close else R.drawable.ic_arrow_forward)

            etIndustry.setText(filter.industryName ?: "")
            arrowForward2.setImageResource(if (filter.industryName != null) R.drawable.ic_close else R.drawable.ic_arrow_forward)

            expectedSalary.setText(filter.salary?.toString() ?: "")
            materialCheckBox.isChecked = filter.onlyWithSalary
        }
    }

    private fun updateButtonsVisibility(isFilterApplied: Boolean) {
        binding.applyButton.isVisible = isFilterApplied
        binding.resetButton.isVisible = isFilterApplied
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null

    }
}
