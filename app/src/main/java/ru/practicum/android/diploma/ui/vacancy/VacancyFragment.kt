package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.color.MaterialColors
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails

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
        }

        viewModel.observeFavoriteState().observe(viewLifecycleOwner) { isFavorite ->
            renderFavoriteState(isFavorite)
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
}
