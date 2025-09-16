package ru.practicum.android.diploma.ui.workplacechoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceChoiceBinding
import ru.practicum.android.diploma.presentation.workplacechoice.WorkplaceViewModel
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController

class WorkplaceChoiceFragment : Fragment() {
    private var _binding: FragmentWorkplaceChoiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkplaceViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkplaceChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

        parentFragmentManager.setFragmentResultListener(
            "country_selection",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val countryId = bundle.getInt("country_id")
            val countryName = bundle.getString("country_name")
            viewModel.setTempCountry(countryId, countryName)
        }

        parentFragmentManager.setFragmentResultListener(
            "area_selection",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val areaId = bundle.getInt("area_id")
            val areaName = bundle.getString("area_name")
            viewModel.setTempArea(areaId, areaName)
        }
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
            viewModel.saveArea()
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { (country, area) ->
            showContent(country, area)
        }
    }

    private fun showContent(country: String?, area: String?) {
        binding.apply {
            setupCountryField(country)
            setupAreaField(area)
        }
    }

    private fun setupCountryField(country: String?) {
        binding.countryEditText.setText(country.orEmpty())
        val hasText = country != null && country.isNotEmpty()
        val iconRes = if (hasText) R.drawable.ic_close else R.drawable.ic_arrow_forward
        binding.arrowForward.setImageResource(iconRes)

        binding.countryEditText.setOnClickListener {
            if (hasText) {
                viewModel.clearCountrySelection()
            } else {
                findNavController().navigate(R.id.action_workplaceChoiceFragment_to_countryChoiceFragment)
            }
        }
    }

    private fun setupAreaField(area: String?) {
        binding.regionEditText.setText(area.orEmpty())
        val hasText = area != null && area.isNotEmpty()
        val iconRes = if (hasText) R.drawable.ic_close else R.drawable.ic_arrow_forward
        binding.arrowForward2.setImageResource(iconRes)

        binding.regionEditText.setOnClickListener {
            if (hasText) {
                viewModel.clearAreaSelection()
            } else {
                navigateToRegionChoice()
            }
        }
    }

    private fun navigateToRegionChoice() {
        val countryId = viewModel.getTempCountry().first
        val bundle = Bundle().apply {
            putInt("country_id", countryId ?: 0)
        }
        findNavController().navigate(R.id.action_workplaceChoiceFragment_to_regionChoiceFragment, bundle)
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
