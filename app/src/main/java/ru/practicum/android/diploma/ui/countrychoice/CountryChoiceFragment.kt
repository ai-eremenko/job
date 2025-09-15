package ru.practicum.android.diploma.ui.countrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentCountryChoiceBinding
import ru.practicum.android.diploma.domain.country.Area
import ru.practicum.android.diploma.ui.countrychoice.adapter.AreaAdapter

class CountryChoiceFragment : Fragment() {

    private var _binding: FragmentCountryChoiceBinding? = null
    private val binding get() = _binding!!

    private val adapter: AreaAdapter by lazy {
        AreaAdapter { item ->
            // обработка выбора страны/региона
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.setItems(
            mutableListOf(
                Area(ID_RUSSIA, null, "Россия"),
                Area(ID_USA, null, "США"),
                Area(ID_GERMANY, null, "Германия")
            )
        )
    }

    companion object {
        private const val ID_RUSSIA = 1
        private const val ID_USA = 2
        private const val ID_GERMANY = 3
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
