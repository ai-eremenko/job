package ru.practicum.android.diploma.ui.countrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryChoiceBinding
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.countrychoice.CountryViewModel
import ru.practicum.android.diploma.presentation.countrychoice.models.CountryScreenState
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import ru.practicum.android.diploma.util.debounce
import kotlin.getValue

class CountryChoiceFragment : Fragment() {
    private var _binding: FragmentCountryChoiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountryViewModel by viewModel()
    //private val countriesAdapter = CountriesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCountryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

        //binding.recyclerView.adapter = countriesAdapter
        val onCountryClickDebounce: (Area) -> Unit =
            debounce<Area>(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { area ->
                viewModel.saveCountry(area)
                findNavController().navigateUp()
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)
    }

    private fun setupObservers() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryScreenState.Content -> showContent(state.list)
                CountryScreenState.Empty -> showError()
            }

        }
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun showContent(list: List<Area>) {
        binding.errorPlaceholder.isVisible = false
        //countriesAdapter.setList(list)
    }

    private fun showError() {
        binding.errorPlaceholder.isVisible = true
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
