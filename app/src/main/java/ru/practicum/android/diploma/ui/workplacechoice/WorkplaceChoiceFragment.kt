package ru.practicum.android.diploma.ui.workplacechoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceChoiceBinding
import ru.practicum.android.diploma.presentation.workplacechoice.WorkplaceViewModel
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController

class WorkplaceChoiceFragment : Fragment() {
    private var _binding: FragmentWorkplaceChoiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkplaceViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkplaceChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)
        viewModel.updateContent()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.resetToSavedSettings()
            findNavController().navigateUp()
        }

        binding.countryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceChoiceFragment_to_countryChoiceFragment)
        }

        binding.regionEditText.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceChoiceFragment_to_regionChoiceFragment)
        }
        binding.applyButton.setOnClickListener {
            viewModel.saveArea()
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { (country, area) ->
            showContent(country, area)
        }
    }

    private fun showContent(country: String?, area: String?) {
        binding.apply {
            setupTextField(regionEditText, arrowForward, area, ::onAreaClearClicked)
            setupTextField(countryEditText, arrowForward2, country, ::onCountryClearClicked)
        }
    }

    private fun setupTextField(
        editText: EditText,
        imageView: ImageView,
        text: String?,
        onClearClick: () -> Unit
    ) {
        editText.setText(text.orEmpty())
        val iconRes = if (text != null) R.drawable.ic_close else R.drawable.ic_arrow_forward
        imageView.setImageResource(iconRes)

        imageView.setOnClickListener {
            if (text != null) onClearClick()
        }
    }

    private fun onAreaClearClicked() {
        viewModel.clearAreaSelection()
    }

    private fun onCountryClearClicked() {
        viewModel.clearCountrySelection()
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
