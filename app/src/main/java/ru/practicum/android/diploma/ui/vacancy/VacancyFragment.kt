package ru.practicum.android.diploma.ui.vacancy

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.vacancy.models.Phone
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.presentation.vacancy.models.NavigationEventState
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<VacancyFragmentArgs>()

    private val viewModel by viewModel<VacancyViewModel> {
        parametersOf(args.vacancyId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)

        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is VacancyScreenState.Content -> setupContent(screenState)
                VacancyScreenState.Loading -> changeContentVisibility(loading = true)
                is VacancyScreenState.Favorite -> setupFavoriteIcon(screenState)
                is VacancyScreenState.Error -> showError(screenState)
            }
        }

        viewModel.getNavigationEvents().observe(viewLifecycleOwner) { state ->
            openApp(state)
        }
    }

    private fun setupClickListeners() {
        binding.ivFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        binding.ivSharing.setOnClickListener {
            viewModel.share()
        }

        binding.tvEmail.setOnClickListener {
            viewModel.sendEmail()
        }
    }

    private fun setupContent(content: VacancyScreenState.Content) {
        binding.apply {
            tvNameVacancy.text = content.vacancyModel.name
            tvSalaryVacancy.text = content.vacancyModel.salary
            tvDescription.text = content.vacancyModel.description
            // остальные поля с проверкой на null
            showPhones(content.vacancyModel.contacts?.phones)
        }
        changeContentVisibility(false)
    }

    private fun setupFavoriteIcon(content: VacancyScreenState.Favorite) {
        if (content.favoriteIcon != null) binding.ivFavorite.setImageDrawable(content.favoriteIcon)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.svVacancyContent.isVisible = !loading
    }

    private fun showError(state: VacancyScreenState.Error) {
        binding.progressBar.isVisible = false
        binding.svVacancyContent.isVisible = false
        binding.ivError.setImageDrawable(state.errorImg)
        binding.tvError.setText(state.errorText)
        binding.error.isVisible = true
    }

    private fun showPhones(phones: List<Phone>?) {
        phones?.forEach { phone ->
            val phoneView = LayoutInflater.from(requireContext())
                .inflate(R.layout.phone_item, binding.phonesContainer, false)

            phoneView.findViewById<TextView>(R.id.tv_phone_number).text = phone.formatted
            phoneView.findViewById<TextView>(R.id.tv_phone_comment).text = phone.comment ?: ""

            phoneView.setOnClickListener {
                viewModel.call(phone.formatted)
            }

            binding.phonesContainer.addView(phoneView)
        }
    }

    private fun openApp(state: NavigationEventState) {
        try {
            startActivity(state.intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "$state.errorMessage $e", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDestroyView() {
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
