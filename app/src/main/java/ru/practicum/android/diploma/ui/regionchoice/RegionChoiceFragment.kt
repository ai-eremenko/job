package ru.practicum.android.diploma.ui.regionchoice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.regionchoice.RegionState
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegionChoiceFragment : Fragment(R.layout.fragment_region_choice) {

    private val viewModel: RegionViewModel by viewModel()

    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }
    private val inputRegion by lazy { requireView().findViewById<EditText>(R.id.searchRegion) }
    private val searchIcon by lazy { requireView().findViewById<ImageView>(R.id.searchFieldIcon) }
    private val clearIcon by lazy { requireView().findViewById<ImageView>(R.id.clearIcon) }
    private val noRegionPlaceholder by lazy { requireView().findViewById<TextView>(R.id.noSuchRegionPlaceholder) }
    private val errorPlaceholder by lazy { requireView().findViewById<TextView>(R.id.errorPlaceholder) }

    private val adapter by lazy {
        RegionAdapter(emptyList()) { area ->
            viewModel.saveAndExit(area) {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()
        observeViewModel()
        viewModel.search("")
    }

    private fun setupAdapter() {
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        inputRegion.addTextChangedListener { text ->
            viewModel.search(text.toString())
            clearIcon.visibility = if (text.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        }

        clearIcon.setOnClickListener {
            inputRegion.text.clear()
        }
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
        noRegionPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
        searchIcon.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        recyclerView.visibility = View.GONE
        noRegionPlaceholder.visibility = View.VISIBLE
        errorPlaceholder.visibility = View.GONE
    }

    private fun showError() {
        recyclerView.visibility = View.GONE
        noRegionPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.VISIBLE
    }

    private fun showContent(list: List<Area>) {
        recyclerView.visibility = View.VISIBLE
        adapter.updateList(list)
        noRegionPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
    }
}
