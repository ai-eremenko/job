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
import ru.practicum.android.diploma.util.Resource

class RegionChoiceFragment : Fragment(R.layout.fragment_select_region) {

//    private val testInteractor = object : AreasInteractor {
//        override suspend fun getAreas(): ru.practicum.android.diploma.util.Resource<List<Area>> {
//            delay(DELAY_500_MS)
//            return Resource.Success(
//                listOf(
//                    Area(MOSCOW_ID, "Москва"),
//                    Area(SPB_ID, "Санкт-Петербург"),
//                    Area(NOVOSIB_ID, "Новосибирск"),
//                    Area(EKB_ID, "Екатеринбург")
//                )
//            )
//        }
//    }

//    private val viewModel: RegionViewModel by viewModels {
//        RegionViewModelFactory(testInteractor)
//    }

//    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }
//    private val progressBar by lazy { requireView().findViewById<ProgressBar>(R.id.progressBar) }
//    private val groupNotFound by lazy { requireView().findViewById<Group>(R.id.group_not_found) }
//    private val groupError by lazy { requireView().findViewById<Group>(R.id.group_error) }
//    private val inputRegion by lazy { requireView().findViewById<TextInputEditText>(R.id.input_region) }
//    private val adapter by lazy {
//        RegionAdapter(emptyList()) { area ->
//            viewModel.saveAndExit(area) {
//                requireActivity().onBackPressedDispatcher.onBackPressed()
//            }
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupAdapter()
//        setupListeners()
//        observeViewModel()
//        viewModel.search("")
//    }
//
//    private fun setupAdapter() {
//        recyclerView.adapter = adapter
//    }
//
//    private fun setupListeners() {
//        inputRegion.addTextChangedListener { text ->
//            viewModel.search(text.toString())
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel.screenState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is RegionState.Loading -> showLoading()
//                is RegionState.Empty -> showEmpty()
//                is RegionState.Error -> showError()
//                is RegionState.Content -> showContent(state.areasList)
//            }
//        }
//    }
//
//    private fun showLoading() {
//        progressBar.visibility = View.VISIBLE
//        recyclerView.visibility = View.GONE
//        groupNotFound.visibility = View.GONE
//        groupError.visibility = View.GONE
//    }
//
//    private fun showEmpty() {
//        progressBar.visibility = View.GONE
//        recyclerView.visibility = View.GONE
//        groupNotFound.visibility = View.VISIBLE
//        groupError.visibility = View.GONE
//    }
//
//    private fun showError() {
//        progressBar.visibility = View.GONE
//        recyclerView.visibility = View.GONE
//        groupNotFound.visibility = View.GONE
//        groupError.visibility = View.VISIBLE
//    }
//
//    private fun showContent(list: List<Area>) {
//        progressBar.visibility = View.GONE
//        recyclerView.visibility = View.VISIBLE
//        groupNotFound.visibility = View.GONE
//        groupError.visibility = View.GONE
//        adapter.updateList(list)
//    }
//
//    companion object {
//        private const val MOSCOW_ID = 1
//        private const val SPB_ID = 2
//        private const val NOVOSIB_ID = 3
//        private const val EKB_ID = 4
//        private const val DELAY_500_MS = 500L
//    }
}
