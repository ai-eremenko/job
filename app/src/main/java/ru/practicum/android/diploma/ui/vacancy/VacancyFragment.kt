package ru.practicum.android.diploma.ui.vacancy

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.vacancy.models.Contacts
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.presentation.vacancy.models.NavigationEventState
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import ru.practicum.android.diploma.util.Dimensions
import ru.practicum.android.diploma.util.formatDescription
import ru.practicum.android.diploma.util.toPx

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
                is VacancyScreenState.Content -> {
                    setupContent(screenState)
                }

                VacancyScreenState.Loading -> {
                    changeContentVisibility(loading = true)
                }

                is VacancyScreenState.Favorite -> {
                    setupFavoriteIcon(screenState)
                }

                is VacancyScreenState.Error -> {
                    showError(screenState)
                }
            }
        }

        viewModel.getNavigationEvents().observe(viewLifecycleOwner) { state ->
            openApp(state)
        }
    }

    private fun setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.icFavorites.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        binding.ivSharing.setOnClickListener {
            viewModel.share()
        }
    }

    private fun setupContent(content: VacancyScreenState.Content) {
        binding.apply {
            tvNameVacancy.text = content.vacancyModel.name
            tvSalaryVacancy.text = content.vacancyModel.salary

            Glide.with(this@VacancyFragment)
                .load(content.vacancyModel.url)
                .placeholder(R.drawable.placeholder_vacancy_preview)
                .centerCrop()
                .transform(RoundedCorners(requireContext().toPx(Dimensions.CORNER_RADIUS_12)))
                .into(binding.icCompany)
            tvNameCompany.text = content.vacancyModel.employerName
            tvAddress.text = content.vacancyModel.address
            tvExperience.text = content.vacancyModel.experience
            tvDescription.text = formatDescription(requireContext(), content.vacancyModel.description)
            showSkills(content.vacancyModel.skills)
            showContacts(content.vacancyModel.contacts)
        }
        changeContentVisibility(false)
    }

    private fun setupFavoriteIcon(content: VacancyScreenState.Favorite) {
        if (content.favoriteIcon != null) binding.icFavorites.setImageDrawable(content.favoriteIcon)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.scrollView.isVisible = !loading
    }

    private fun showError(state: VacancyScreenState.Error) {
        binding.progressBar.isVisible = false
        binding.scrollView.isVisible = false
        binding.ivError.setImageDrawable(state.errorImg)
        binding.tvError.setText(state.errorText)
        binding.error.isVisible = true
    }

    private fun showSkills(skills: List<String>?) {
        if (skills != null) {
            binding.tvSkillsTitle.visibility = View.VISIBLE
            val formattedSkills = skills.joinToString(
                separator = "\n  •  ",
                prefix = "  •  ",
                transform = { it.trim() }
            )
            binding.tvSkills.text = formattedSkills
            binding.tvSkills.visibility = View.VISIBLE
        }
    }

    private fun showContacts(contacts: Contacts?) {
        if (contacts == null) return

        val nameView = LayoutInflater.from(requireContext())
            .inflate(R.layout.vacancy_detail_item, binding.contactsContainer, false)
        nameView.findViewById<TextView>(R.id.tv_title).text = getString(R.string.contact_person)
        nameView.findViewById<TextView>(R.id.tv_content).text = contacts.name
        binding.contactsContainer.addView(nameView)

        if (contacts.email.isNotEmpty()) {
            val emailView = LayoutInflater.from(requireContext())
                .inflate(R.layout.vacancy_detail_item, binding.contactsContainer, false)
            emailView.findViewById<TextView>(R.id.tv_title).text = getString(R.string.e_mail)
            emailView.findViewById<TextView>(R.id.tv_content).text = contacts.email
            emailView.findViewById<TextView>(R.id.tv_content)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            emailView.findViewById<TextView>(R.id.tv_content).setOnClickListener {
                viewModel.sendEmail()
            }
            binding.contactsContainer.addView(emailView)
        }
        contacts.phones?.forEach { phone ->
            val phoneView = LayoutInflater.from(requireContext())
                .inflate(R.layout.vacancy_detail_item, binding.contactsContainer, false)

            phoneView.findViewById<TextView>(R.id.tv_title).text = getString(R.string.phone)
            phoneView.findViewById<TextView>(R.id.tv_content).text = phone.formatted
            phoneView.findViewById<TextView>(R.id.tv_content)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            phoneView.findViewById<TextView>(R.id.tv_content).setOnClickListener {
                viewModel.call(phone.formatted)
            }

            binding.contactsContainer.addView(phoneView)
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
