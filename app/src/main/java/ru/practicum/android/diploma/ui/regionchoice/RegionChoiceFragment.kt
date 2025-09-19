package ru.practicum.android.diploma.ui.regionchoice

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.regionchoice.AreasState
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel

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
            viewModel.saveAndExit(area, findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()
        observeViewModel()
        adjustInputPadding()
    }

    private fun setupAdapter() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        inputRegion.addTextChangedListener { text ->
            // Показ крестика и скрытие поиска при вводе
            clearIcon.isVisible = !text.isNullOrEmpty()
            searchIcon.isVisible = text.isNullOrEmpty()
            viewModel.search(text.toString())
        }

        inputRegion.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
            }
            false
        }

        clearIcon.setOnClickListener {
            inputRegion.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(inputRegion.windowToken, 0)
        inputRegion.clearFocus()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AreasState.Loading -> showLoading()
                is AreasState.Empty -> showEmpty()
                is AreasState.Error -> showError()
                is AreasState.Content -> showContent(state.areas)
            }
        }
    }

    private fun showLoading() {
        recyclerView.isVisible = false
        noRegionPlaceholder.isVisible = false
        errorPlaceholder.isVisible = false
        searchIcon.isVisible = inputRegion.text.isEmpty()
        clearIcon.isVisible = inputRegion.text.isNotEmpty()
    }

    private fun showEmpty() {
        recyclerView.isVisible = false
        noRegionPlaceholder.isVisible = true
        errorPlaceholder.isVisible = false
    }

    private fun showError() {
        recyclerView.isVisible = false
        noRegionPlaceholder.isVisible = false
        errorPlaceholder.isVisible = true
    }

    private fun showContent(list: List<Area>) {
        recyclerView.isVisible = true
        adapter.updateList(list)
        noRegionPlaceholder.isVisible = false
        errorPlaceholder.isVisible = false
    }


    private fun adjustInputPadding() {
        val paddingRight = resources.getDimensionPixelSize(R.dimen._56dp)
        inputRegion.setPadding(
            inputRegion.paddingLeft,
            inputRegion.paddingTop,
            paddingRight,
            inputRegion.paddingBottom
        )
    }
}
