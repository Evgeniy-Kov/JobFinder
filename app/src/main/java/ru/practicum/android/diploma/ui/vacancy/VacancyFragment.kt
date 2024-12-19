package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.color.MaterialColors
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.getFormattedSalaryForViewHolder

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding: FragmentVacancyBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: VacancyDetailsViewModel by viewModel()
    private val args by navArgs<VacancyFragmentArgs>()
    private var vacancyDetails: VacancyDetails? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyDetails(args.vacancyId)
        viewModel.observeVacancyDetails().observe(viewLifecycleOwner) { details ->
            vacancyDetails = details
            renderUI(vacancyDetails)
        }

        viewModel.observeFavoriteState().observe(viewLifecycleOwner) { isFavorite ->
            renderFavoriteState(isFavorite)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFavourite.setOnClickListener {
            if (vacancyDetails != null) {
                viewModel.onFavoriteClick(vacancyDetails!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderFavoriteState(isFavourite: Boolean) {
        if (isFavourite) {
            binding.btnFavourite.setImageResource(R.drawable.ic_favorites_checked)
            binding.btnFavourite.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
        } else {
            binding.btnFavourite.setImageResource(R.drawable.ic_favorites)
            binding.btnFavourite.setColorFilter(
                MaterialColors.getColor(binding.btnFavourite, com.google.android.material.R.attr.colorOnPrimary)
            )
        }
    }

    private fun renderUI(details: VacancyDetails?) {
        if (details != null) {
            binding.titleTv.text = details.name
            binding.salaryTv.text =
                getFormattedSalaryForViewHolder(details.salaryFrom, details.salaryTo, requireContext())
            binding.employerNameTv.text = details.employerName
            binding.employerCityTv.text = details.city
            Glide.with(requireContext())
                .load(details.employerLogoUrl)
                .placeholder(R.drawable.vacancy_cover_placeholder)
                .centerInside()
                .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.dp_12)))
                .into(binding.employerLogoIv)

            binding.experienceTv.text = details.experience
            binding.employmentTv.text = details.employment
            binding.descriptionTv.text = Html.fromHtml(details.description, Html.FROM_HTML_MODE_COMPACT)
            if (details.keySkills.isEmpty()) {
                binding.keyskillsTv.visibility = View.GONE
                binding.keyskillsHeader.visibility = View.GONE
            } else {
                binding.keyskillsTv.text = buildString {
                    append(getString(R.string.keyskills_separator))
                    append(details.keySkills.joinToString(getString(R.string.keyskills_separator)))
                }
            }
        }

    }
}


