package ru.practicum.android.diploma.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.presentation.search.SearchScreenState
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.search.adapter.VacancyListAdapter
import ru.practicum.android.diploma.util.ResponseStatus
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchEditTextValue: String = SEARCH_TEXT_DEF

    private val viewModel: SearchViewModel by viewModel()

    private var isNextPageLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var _vacancyAdapter: VacancyListAdapter? = null
    private val vacancyAdapter get() = _vacancyAdapter!!

    private fun vacancyListViewCreate() {
        val onVacancyClickDebounce: (VacancyPreviewPresent) -> Unit =
            debounce<VacancyPreviewPresent>(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
                openVacancy(vacancy)
            }
        _vacancyAdapter = VacancyListAdapter(mutableListOf(), onVacancyClickDebounce)
        binding.recyclerView.adapter = vacancyAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount - 1 && !isNextPageLoading && viewModel.isMorePage()) {
                        binding.progressBar.isVisible = true
                        isNextPageLoading = true
                        viewModel.newPageRequest()
                    }
                }
            }
        })
    }

    private fun searchEditTextCreate() {
        binding.searchField.setText(searchEditTextValue)
        binding.searchFieldIcon.setOnClickListener {
            binding.searchField.setText("")
            vacancyAdapter.updateVacancies(emptyList())
            clearScreen()
            showEmpty()
            hideKeyboard()
        }

        binding.searchField.doOnTextChanged { text, _, _, _ ->
            viewModel.searchDebounce(
                changedText = text?.toString() ?: ""
            )
            searchEditTextValue = binding.searchField.text.toString()

            if (binding.searchField.text.toString().isEmpty()) {
                binding.searchFieldIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_search))
                clearScreen()
                showEmpty()
            } else {
                binding.searchFieldIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_close))
            }
        }
    }

    private fun openVacancy(vacancy: VacancyPreviewPresent) {
        val bundle = bundleOf("vacancy_id" to vacancy.id)
        findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditTextCreate()
        vacancyListViewCreate()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vacancyChannel
                .receiveAsFlow()
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    vacancyAdapter.addVacancies(it)
                    isNextPageLoading = false
                    binding.progressBar.isVisible = false
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastChannel
                .receiveAsFlow()
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    if (it != ResponseStatus.SUCCESS) {
                        isNextPageLoading = false
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.error_list),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _vacancyAdapter = null
    }

    private fun render(state: SearchScreenState) {
        clearScreen()
        when (state) {
            is SearchScreenState.Empty -> showEmpty()
            is SearchScreenState.Loading -> showLoading()
            is SearchScreenState.Content -> showVacancy(state.vacancy, state.countVacancy)
            is SearchScreenState.EmptyError -> showEmptyError()
            is SearchScreenState.NetworkError -> showNetworkError()
        }
    }

    private fun showEmpty() {
        binding.searchScreenCover.isVisible = true
    }

    private fun showLoading() {
        hideKeyboard()
        binding.progressBar.isVisible = true
    }

    private fun showEmptyError() {
        binding.searchStatus.isVisible = true
        binding.searchStatus.text = requireContext().getString(R.string.there_are_no_vacancies)
        binding.errorPlaceholder.isVisible = true
        binding.errorPlaceholder.text = requireContext().getString(R.string.no_get_list_vacancies)
        binding.errorPlaceholder.setCompoundDrawablesWithIntrinsicBounds(
            null,
            requireContext().getDrawable(R.drawable.cat_with_plate),
            null,
            null
        )
    }

    private fun showNetworkError() {
        binding.errorPlaceholder.isVisible = true
        binding.errorPlaceholder.text = requireContext().getString(R.string.no_internet)
        binding.errorPlaceholder.setCompoundDrawablesWithIntrinsicBounds(
            null,
            requireContext().getDrawable(R.drawable.img_internet_connection_error),
            null,
            null
        )
    }

    private fun showVacancy(vacancy: List<VacancyPreviewPresent>, countVacancy: Int) {
        binding.recyclerView.isVisible = true
        binding.searchStatus.isVisible = true
        binding.searchStatus.text = requireContext().getString(R.string.vacancies_found, countVacancy)
        vacancyAdapter.updateVacancies(vacancy)
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchField.windowToken, 0)
    }

    private fun clearScreen() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.searchStatus.isVisible = false
        binding.errorPlaceholder.isVisible = false
        binding.searchScreenCover.isVisible = false
    }

    companion object {
        private const val SEARCH_TEXT_DEF = ""
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
