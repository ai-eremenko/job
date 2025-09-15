package ru.practicum.android.diploma.ui.industrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentIndustryChoiceBinding
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryAdapter
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryItemUi

class IndustryChoiceFragment : Fragment() {

    private var _binding: FragmentIndustryChoiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IndustryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IndustryAdapter { item ->
            // Обработка выбора отрасли
        }

        binding.recyclerIndustries.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerIndustries.adapter = adapter

        adapter.setItems(
            mutableListOf(
                IndustryItemUi("1", "IT", false),
                IndustryItemUi("2", "Финансы", false),
                IndustryItemUi("3", "Строительство", true)
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
