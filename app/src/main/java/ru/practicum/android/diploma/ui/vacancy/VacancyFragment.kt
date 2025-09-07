package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import kotlin.getValue

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<VacancyFragmentArgs>()

    private val viewModel by viewModel<VacancyViewModel> {
        parametersOf(args.id)
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
        //(activity as? NavigationVisibilityController)?.setNavigationVisibility(false)

        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {

        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is VacancyScreenState.Content -> setupContent(screenState)
                VacancyScreenState.Loading -> changeContentVisibility(loading = true)
                VacancyScreenState.ErrorNotFound -> showError()
                is VacancyScreenState.Favorite -> setupFavoriteIcon(screenState)
            }
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

        //Добавить обработку нажатия на номер телефона
    }

    private fun setupContent(content: VacancyScreenState.Content) {
        binding.tvNameVacancy.text = content.vacancyModel.name
        binding.tvSalaryVacancy.text = content.vacancyModel.salary
        binding.tvDescription.text = content.vacancyModel.description
        //остальные поля с проверкой на null
        changeContentVisibility(false)
    }

    private fun setupFavoriteIcon(content: VacancyScreenState.Favorite) {
        if (content.favoriteIcon != null) binding.ivFavorite.setImageDrawable(content.favoriteIcon)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.svVacancyContent.isVisible = !loading
    }

    private fun showError() {
        binding.progressBar.isVisible = false
        binding.svVacancyContent.isVisible = false
        binding.error.isVisible = true
    }

    override fun onDestroyView() {
        //(activity as? NavigationVisibilityController)?.setNavigationVisibility(true)
        super.onDestroyView()
        _binding = null
    }
}
