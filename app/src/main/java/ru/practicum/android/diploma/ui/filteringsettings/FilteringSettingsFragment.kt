package ru.practicum.android.diploma.ui.filteringsettings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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

class FilteringSettingsFragment : Fragment() {

    private var _binding: FragmentFilteringSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()
    private var hasUserInteracted = false
    private var isInitialLoad = true
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.clearIcon.visibility = clearButtonVisibility(s)
            if (!isInitialLoad) {
                hasUserInteracted = true
                updateButtonsVisibility()
            }
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

        binding.applyButton.setOnClickListener {
            sharedViewModel.notifyFiltersApplied()
            hasUserInteracted = false
            findNavController().navigateUp()
        }

        binding.resetButton.setOnClickListener {
            viewModel.clearFilter()
        }
        setupCheckboxListener()
        setupWorkplaceListener()
        setupIndustryListener()
        setupSalaryListener()
    }

    private fun setupObserves() {
        viewModel.getFilterStateLiveData().observe(viewLifecycleOwner) { state ->
            binding.expectedSalary.removeTextChangedListener(simpleTextWatcher)
            when (state) {
                is FilterScreenState.Content -> {
                    showContent(state.filter)
                    updateButtonsVisibility()
                }

                FilterScreenState.Empty -> {
                    showEmptyState()
                    updateButtonsVisibility()
                }
            }
            binding.expectedSalary.addTextChangedListener(simpleTextWatcher)
            isInitialLoad = false
        }
    }

    private fun setupSalaryListener() {
        simpleTextWatcher.let { binding.expectedSalary.addTextChangedListener(it) }

        binding.expectedSalary.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = binding.expectedSalary.text?.toString() ?: ""
                updateSalary(text)
                true
            } else {
                false
            }
        }

        binding.expectedSalary.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.expectedSalary.text?.toString() ?: ""
                updateSalary(text)
            }
        }

        binding.clearIcon.setOnClickListener {
            binding.expectedSalary.setText("")
            updateSalary("")
        }
    }

    private fun setupCheckboxListener() {
        binding.materialCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            binding.expectedSalary.clearFocus()
            if (!isInitialLoad) {
                hasUserInteracted = true
                updateButtonsVisibility()
            }
            viewModel.updateOnlyWithSalary(isChecked)
        }
    }

    private fun setupWorkplaceListener() {
        binding.etWorkplace.setOnClickListener {
            val currentFilter = when (val state = viewModel.getFilterStateLiveData().value) {
                is FilterScreenState.Content -> state.filter
                else -> FilterSettings()
            }
            val hasWorkplace = currentFilter.countryName != null || currentFilter.areaName != null
            if (hasWorkplace) {
                if (!isInitialLoad) {
                    hasUserInteracted = true
                    updateButtonsVisibility()
                }
                viewModel.clearWorkplaceSelection()
            } else {
                findNavController()
                    .navigate(
                        FilteringSettingsFragmentDirections
                            .actionFilteringSettingsFragmentToChoiceOfWorkplaceFragment()
                    )
            }
        }
    }

    private fun setupIndustryListener() {
        binding.etIndustry.setOnClickListener {
            val currentFilter = when (val state = viewModel.getFilterStateLiveData().value) {
                is FilterScreenState.Content -> state.filter
                else -> FilterSettings()
            }
            if (currentFilter.industryName != null) {
                if (!isInitialLoad) {
                    hasUserInteracted = true
                    updateButtonsVisibility()
                }
                viewModel.clearIndustrySelection()
            } else {
                findNavController()
                    .navigate(
                        FilteringSettingsFragmentDirections
                            .actionFilteringSettingsFragmentToIndustryChoiceFragment()
                    )
            }
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

    private fun updateButtonsVisibility() {
        val shouldShowButtons = hasUserInteracted
        binding.applyButton.isVisible = shouldShowButtons
        binding.resetButton.isVisible = shouldShowButtons
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun updateSalary(salary: String) {
        if (!isInitialLoad) {
            hasUserInteracted = true
            updateButtonsVisibility()
        }
        viewModel.updateSalary(salary)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.expectedSalary.windowToken, 0)
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        simpleTextWatcher.let { binding.expectedSalary.removeTextChangedListener(it) }
        _binding = null
    }
}
