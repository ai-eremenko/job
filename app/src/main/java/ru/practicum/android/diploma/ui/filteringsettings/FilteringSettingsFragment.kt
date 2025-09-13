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
    private lateinit var simpleTextWatcher: TextWatcher

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
        setupSalaryListener()
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
            binding.expectedSalary.clearFocus()
            viewModel.updateOnlyWithSalary(isChecked)
        }

        binding.tilWorkplace.setOnClickListener {
            val direction = FilteringSettingsFragmentDirections
                .actionFilteringSettingsFragmentToChoiceOfWorkplaceFragment()
            findNavController().navigate(direction)
        }

        binding.tilIndustry.setOnClickListener {
            val direction = FilteringSettingsFragmentDirections
                .actionFilteringSettingsFragmentToIndustryChoiceFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setupObserves() {
        viewModel.getFilterStateLiveData().observe(viewLifecycleOwner) {
            setupContent(it)
        }
    }

    private fun setupSalaryListener() {
        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

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

    private fun setupContent(filter: FilterSettings) {
        showContent(filter)
        updateButtonsVisibility(filter.checkActiveFilters())
    }

    private fun showContent(filter: FilterSettings) {
        with(binding) {
            etWorkplace.setText(filter.areaName ?: "")
            arrowForward.setImageResource(
                if (filter.areaName != null) {
                    R.drawable.ic_close
                } else {
                    R.drawable.ic_arrow_forward
                }
            )

            etIndustry.setText(filter.industryName ?: "")
            arrowForward2.setImageResource(
                if (filter.industryName != null) {
                    R.drawable.ic_close
                } else {
                    R.drawable.ic_arrow_forward
                }
            )

            expectedSalary.setText(filter.salary?.toString() ?: "")
            materialCheckBox.isChecked = filter.onlyWithSalary
        }
    }

    private fun updateButtonsVisibility(isFilterApplied: Boolean) {
        binding.applyButton.isVisible = isFilterApplied
        binding.resetButton.isVisible = isFilterApplied
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
