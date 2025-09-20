package ru.practicum.android.diploma.ui.regionchoice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.regionchoice.RegionState
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel

class RegionChoiceFragment : Fragment(R.layout.fragment_region_choice) {

    private val args by navArgs<RegionChoiceFragmentArgs>()
    private val viewModel: RegionViewModel by viewModel()

    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }
    private val searchInput by lazy { requireView().findViewById<EditText>(R.id.searchRegion) }
    private val clearIcon by lazy { requireView().findViewById<ImageView>(R.id.clearIcon) }
    private val placeholder by lazy { requireView().findViewById<TextView>(R.id.noSuchRegionPlaceholder) }
    private val errorPlaceholder by lazy { requireView().findViewById<TextView>(R.id.errorPlaceholder) }
    private val toolbar by lazy { requireView().findViewById<Toolbar>(R.id.toolbar) }

    private var selectedCountryId: Int? = null

    private val adapter by lazy {
        RegionAdapter(emptyList()) { area ->
            viewModel.selectArea(area) { selectedArea ->
                // Передаем выбранный регион через FragmentResult
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedCountryId = args.countryId.takeIf { it != 0 }

        recyclerView.adapter = adapter
        setupListeners()
        observeViewModel()

        viewModel.search("", selectedCountryId)

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        searchInput.addTextChangedListener { text ->
            val query = text.toString()
            viewModel.search(query, selectedCountryId)
            clearIcon.visibility = if (query.isEmpty()) View.INVISIBLE else View.VISIBLE
        }
        clearIcon.setOnClickListener { searchInput.setText("") }
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Loading -> showLoading()
                is RegionState.Empty -> showEmpty()
                is RegionState.Error -> showError()
                is RegionState.Content -> showContent(state.areasList)
            }
        }
    }

    private fun showLoading() {
        recyclerView.visibility = View.GONE
        placeholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
    }

    private fun showEmpty() {
        recyclerView.visibility = View.GONE
        placeholder.visibility = View.VISIBLE
        errorPlaceholder.visibility = View.GONE
    }

    private fun showError() {
        recyclerView.visibility = View.GONE
        placeholder.visibility = View.GONE
        errorPlaceholder.visibility = View.VISIBLE
    }

    private fun showContent(list: List<Area>) {
        recyclerView.visibility = View.VISIBLE
        placeholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
        adapter.updateList(list)
    }
}
