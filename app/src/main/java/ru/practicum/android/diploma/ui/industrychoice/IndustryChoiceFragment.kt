package ru.practicum.android.diploma.ui.industrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryAdapter
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryItemUi

class IndustryChoiceFragment : Fragment() {

    private val adapter: IndustryAdapter by lazy {
        IndustryAdapter(emptyList()) { item ->
            // обработка выбора отрасли
        }
    }
    private val recycler: RecyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.recyclerIndustries)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_industry_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        adapter.update(
            listOf(
                IndustryItemUi("1", "IT", false),
                IndustryItemUi("2", "Финансы", false),
                IndustryItemUi("3", "Строительство", true)
            )
        )
    }
}
