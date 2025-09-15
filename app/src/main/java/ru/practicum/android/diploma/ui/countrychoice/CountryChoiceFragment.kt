package ru.practicum.android.diploma.ui.countrychoice

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.countrychoice.adapter.AreaAdapter
import ru.practicum.android.diploma.ui.countrychoice.adapter.AreaItemUi

class CountryChoiceFragment : Fragment() {

    private val recycler: RecyclerView by lazy {
        requireView().findViewById(R.id.recyclerAreas)
    }

    private val adapter: AreaAdapter by lazy {
        AreaAdapter(
            emptyList()
        ) { item ->
            // обработка выбора страны
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        adapter.update(
            listOf(
                AreaItemUi("1", "Россия"),
                AreaItemUi("2", "США"),
                AreaItemUi("3", "Германия")
            )
        )
    }
}
