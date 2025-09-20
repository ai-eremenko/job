package ru.practicum.android.diploma.ui.regionchoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.databinding.FragmentRegionChoiceBinding
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel
import ru.practicum.android.diploma.presentation.regionchoice.models.RegionState
import ru.practicum.android.diploma.ui.countrychoice.adapter.AreaAdapter
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import kotlin.getValue

class RegionChoiceFragment : Fragment() {

    private val args by navArgs<RegionChoiceFragmentArgs>()
    private var _binding: FragmentRegionChoiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegionViewModel> {
        parametersOf(args.countryId)
    }
    private val adapter by lazy {
        AreaAdapter { area ->
            viewModel.selectArea(area) { selectedArea ->
                parentFragmentManager.setFragmentResult(
                    "area_selection",
                    bundleOf(
                        "area_id" to selectedArea.id,
                        "area_name" to selectedArea.name
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegionChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        setupListeners()
        observeViewModel()


    }

    override fun onResume() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)
        super.onResume()
    }

    private fun setupListeners() {
        binding.searchRegion.addTextChangedListener { text ->
            val query = text.toString()
            adapter.filter(query)
            checkFilterResults(query)
            if (query.isEmpty()) {
                binding.clearIcon.visibility = View.INVISIBLE
                binding.searchFieldIcon.isVisible = true
            } else {
                binding.clearIcon.visibility = View.VISIBLE
                binding.searchFieldIcon.isVisible = false
            }
        }

        binding.clearIcon.setOnClickListener {
            binding.searchRegion.setText("")
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Empty -> showEmpty()
                is RegionState.Error -> showError()
                is RegionState.Content -> {
                    adapter.setItems(state.areasList.toMutableList())
                }
            }
        }
    }

    private fun showError() {
        binding.noSuchRegionPlaceholder.visibility = View.VISIBLE
        binding.errorPlaceholder.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.noSuchRegionPlaceholder.visibility = View.GONE
        binding.errorPlaceholder.visibility = View.VISIBLE
    }

    private fun showContent() {
        binding.noSuchRegionPlaceholder.visibility = View.GONE
        binding.errorPlaceholder.visibility = View.GONE
    }

    private fun checkFilterResults(query: String) {
        if (query.isNotEmpty()) {
            if (adapter.itemCount == 0) {
                showError()
            } else {
                showContent()
            }
        } else {
            showContent()
        }
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
