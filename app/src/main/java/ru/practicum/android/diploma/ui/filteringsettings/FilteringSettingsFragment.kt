package ru.practicum.android.diploma.ui.filteringsettings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.presentation.filteringsettings.FilterViewModel
import ru.practicum.android.diploma.presentation.filteringsettings.SharedViewModel
import ru.practicum.android.diploma.presentation.filteringsettings.models.FilterScreenState
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import ru.practicum.android.diploma.util.KeyboardUtils

class FilteringSettingsFragment : Fragment() {

    private var _binding: FragmentFilteringSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()

    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.clearIcon.visibility = clearButtonVisibility(s)
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

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

        setupListeners()
        setupObserves()
    }

    override fun onResume() {
        super.onResume()
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)
        viewModel.updateContent()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupActionButtons()
        setupCheckboxListener()
        setupWorkplaceListener()
        setupIndustryListener()
        setupSalaryListener()
    }

    private fun setupObserves() {
        viewModel.getFilterStateLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is FilterScreenState.Content -> showContent(state.filter)
                FilterScreenState.Empty -> showEmptyState()
            }
        }
        viewModel.getHasChangesLiveData().observe(viewLifecycleOwner) { hasChanges ->
            binding.applyButton.isVisible = hasChanges
            binding.resetButton.isVisible = hasChanges
        }
    }

    private fun setupActionButtons() {
        with(binding) {
            applyButton.setOnClickListener {
                sharedViewModel.notifyFiltersApplied()
                viewModel.resetChangesFlag()
                findNavController().navigateUp()
            }

            resetButton.setOnClickListener {
                viewModel.clearFilter()
            }
        }
    }

    private fun setupSalaryListener() {
        simpleTextWatcher.let { binding.expectedSalary.addTextChangedListener(it) }
        with(binding) {
            expectedSalary.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateSalary(expectedSalary.text?.toString() ?: "")
                    true
                } else {
                    false
                }
            }

            expectedSalary.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    updateSalary(expectedSalary.text?.toString() ?: "")
                }
            }

            clearIcon.setOnClickListener {
                expectedSalary.setText("")
                updateSalary("")
            }
        }
    }

    private fun setupCheckboxListener() {
        binding.materialCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.expectedSalary.clearFocus()
            viewModel.updateOnlyWithSalary(isChecked)
        }
    }

    private fun setupWorkplaceListener() {
        binding.etWorkplace.setOnClickListener {
            val currentFilter = getCurrentFilter()
            val hasWorkplace = currentFilter.countryName != null || currentFilter.areaName != null
            if (hasWorkplace) {
                viewModel.clearWorkplaceSelection()
            } else {
                findNavController().navigate(
                    FilteringSettingsFragmentDirections
                        .actionFilteringSettingsFragmentToChoiceOfWorkplaceFragment()
                )
            }
        }
    }

    private fun setupIndustryListener() {
        binding.etIndustry.setOnClickListener {
            val currentFilter = getCurrentFilter()
            if (currentFilter.industryName != null) {
                viewModel.clearIndustrySelection()
            } else {
                findNavController().navigate(
                    FilteringSettingsFragmentDirections
                        .actionFilteringSettingsFragmentToIndustryChoiceFragment()
                )
            }
        }
    }

    private fun getCurrentFilter(): FilterSettings {
        return when (val state = viewModel.getFilterStateLiveData().value) {
            is FilterScreenState.Content -> state.filter
            else -> FilterSettings()
        }
    }

    private fun showEmptyState() {
        with(binding) {
            etWorkplace.setText("")
            arrowForward.setImageResource(R.drawable.ic_arrow_forward)

            etIndustry.setText("")
            arrowForward2.setImageResource(R.drawable.ic_arrow_forward)

            expectedSalary.setText("")
            materialCheckBox.isChecked = false
        }
    }

    private fun showContent(filter: FilterSettings) {
        setupWorkplace(filter)
        setupIndustries(filter)

        with(binding) {
            expectedSalary.setText(filter.salary?.toString() ?: "")
            materialCheckBox.isChecked = filter.onlyWithSalary
        }
    }

    private fun setupWorkplace(filter: FilterSettings) {
        with(binding) {
            if (filter.areaName != null && filter.countryName != null) {
                etWorkplace.setText(getString(R.string.area_string, filter.countryName, filter.areaName))
            } else {
                etWorkplace.setText(filter.countryName ?: "")
            }
            val hasWorkplace = filter.countryName != null || filter.areaName != null
            val workplaceIcon = if (hasWorkplace) R.drawable.ic_close else R.drawable.ic_arrow_forward
            arrowForward.setImageResource(workplaceIcon)
        }
    }

    private fun setupIndustries(filter: FilterSettings) {
        with(binding) {
            etIndustry.setText(filter.industryName ?: "")
            val industryIcon = if (filter.industryName != null) R.drawable.ic_close else R.drawable.ic_arrow_forward
            arrowForward2.setImageResource(industryIcon)
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun updateSalary(salary: String) {
        viewModel.updateSalary(salary)
        KeyboardUtils.hideKeyboard(binding.expectedSalary)
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
