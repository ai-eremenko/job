package ru.practicum.android.diploma.ui.countrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryChoiceBinding
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.countrychoice.CountryViewModel
import ru.practicum.android.diploma.presentation.countrychoice.models.CountryScreenState
import ru.practicum.android.diploma.ui.countrychoice.adapter.AreaAdapter
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import kotlin.getValue

class CountryChoiceFragment : Fragment() {
    private var _binding: FragmentCountryChoiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountryViewModel by viewModel()

    private val countriesAdapter: AreaAdapter by lazy {
        AreaAdapter { item ->
            val result = bundleOf(
                "country_id" to item.id,
                "country_name" to item.name
            )
            parentFragmentManager.setFragmentResult("country_selection", result)
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCountryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

        binding.recyclerView.adapter = countriesAdapter
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
        countriesAdapter.setItems(list.toMutableList())
    }

    private fun showError() {
        binding.errorPlaceholder.isVisible = true
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
