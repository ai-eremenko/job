package ru.practicum.android.diploma.ui.industrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustryChoiceBinding
import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.presentation.industrychoice.IndustryChoiceScreenState
import ru.practicum.android.diploma.presentation.industrychoice.IndustryChoiceViewModel
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryAdapter
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryItemUi
import kotlin.getValue

class IndustryChoiceFragment : Fragment() {

    private var _binding: FragmentIndustryChoiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IndustryChoiceViewModel by viewModel()

    private val adapter: IndustryAdapter by lazy {
        IndustryAdapter { item: IndustryItemUi ->
            // обработка выбора отрасли
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.VISIBLE

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryChoiceScreenState.Content -> showContent(state.list)
                IndustryChoiceScreenState.Empty -> showError()
            }

        }
    }

    private fun showContent(list: List<Industry>) {
        val mutableListOfIndustryItemUi = mutableListOf<IndustryItemUi>()
        list.forEach { industry ->
            mutableListOfIndustryItemUi.add(IndustryItemUi(industry.id.toString(), industry.name, false))
        }
        adapter.setItems(mutableListOfIndustryItemUi)
    }

    private fun showError() {
        binding.recyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
