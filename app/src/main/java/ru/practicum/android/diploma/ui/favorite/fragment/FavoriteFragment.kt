package ru.practicum.android.diploma.ui.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.ui.favorite.state.FavoriteState
import ru.practicum.android.diploma.ui.search.adapter.VacancyListAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()
    private var adapter = VacancyListAdapter(mutableListOf()) { vacancy ->
        val bundle = bundleOf("vacancy_id" to vacancy.id)
        findNavController().navigate(R.id.action_favoriteFragment_to_vacancyFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoriteState.Empty -> {
                    binding.placeholder.visibility = View.VISIBLE
                    binding.errorPlaceholder.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
                is FavoriteState.Content -> {
                    binding.placeholder.visibility = View.GONE
                    binding.errorPlaceholder.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.updateVacancies(state.vacancies)
                }
                is FavoriteState.Error -> {
                    binding.placeholder.visibility = View.GONE
                    binding.errorPlaceholder.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFavorites()
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
