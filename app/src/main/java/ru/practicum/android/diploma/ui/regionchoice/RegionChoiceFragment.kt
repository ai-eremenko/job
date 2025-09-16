package ru.practicum.android.diploma.ui.regionchoice

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.Group
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.regionchoice.RegionState
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModelFactory

class RegionChoiceFragment : Fragment(R.layout.fragment_select_region) {


    private val testInteractor = object : AreasInteractor {
        override suspend fun getAreas(): ru.practicum.android.diploma.util.Resource<List<Area>> {
            delay(500)
            return ru.practicum.android.diploma.util.Resource.Success(
                listOf(
                    Area(1, "Москва"),
                    Area(2, "Санкт-Петербург"),
                    Area(3, "Новосибирск"),
                    Area(4, "Екатеринбург")
                )
            )
        }
    }

    private val viewModel: RegionViewModel by viewModels {
        RegionViewModelFactory(testInteractor)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RegionAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var groupNotFound: Group
    private lateinit var groupError: Group
    private lateinit var inputRegion: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupAdapter()
        setupListeners()
        observeViewModel()
        viewModel.search("")
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        groupNotFound = view.findViewById(R.id.group_not_found)
        groupError = view.findViewById(R.id.group_error)
        inputRegion = view.findViewById(R.id.input_region)
    }

    private fun setupAdapter() {
        adapter = RegionAdapter(emptyList()) { area ->
            viewModel.saveAndExit(area) {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        inputRegion.addTextChangedListener { text ->
            viewModel.search(text.toString())
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
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        groupNotFound.visibility = View.GONE
        groupError.visibility = View.GONE
    }

    private fun showEmpty() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        groupNotFound.visibility = View.VISIBLE
        groupError.visibility = View.GONE
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        groupNotFound.visibility = View.GONE
        groupError.visibility = View.VISIBLE
    }

    private fun showContent(list: List<Area>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        groupNotFound.visibility = View.GONE
        groupError.visibility = View.GONE
        adapter.updateList(list)
    }
}
