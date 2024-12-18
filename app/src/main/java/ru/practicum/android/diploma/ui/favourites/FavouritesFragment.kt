package ru.practicum.android.diploma.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.debounce

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoritesViewModel>()
    private var vacancyDetailsAdapter = VacancyDetailsAdapter()
    private var isClickAllowed = true

    private val debounceClick: (VacancyDetails) -> Unit by lazy {
        debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
            intentVacancyFragment(vacancy)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isClickAllowed = true

        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesState.Content -> {
                    vacancyDetailsAdapter.submitList(state.vacanciesList)
                    showContent(state.vacanciesList)
                }

                is FavoritesState.Error -> showError()
                is FavoritesState.Empty -> showEmpty()
            }
        }

        vacancyDetailsAdapter.onItemClickListener = VacancyDetailsViewHolder.OnItemClickListener { vacancy ->
            debounceClick(vacancy)
        }

        binding.favoritesList.adapter = vacancyDetailsAdapter
    }

    private fun intentVacancyFragment(vacancy: VacancyDetails) {
        val direction = FavouritesFragmentDirections.actionFavouritesFragmentToVacancyFragment(vacancy.id)
        findNavController().navigate(direction)
    }

    private fun showContent(newListVacancies: List<VacancyDetails>) {
        vacancyDetailsAdapter?.submitList(newListVacancies)
        binding.imgError.isVisible = false
        binding.txtError.isVisible = false
        binding.favoritesList.isVisible = true
    }

    private fun showEmpty() {
        binding.imgError.setImageResource(R.drawable.empty_favorites)
        binding.txtError.setText(R.string.empty_favorites)

        binding.imgError.isVisible = true
        binding.txtError.isVisible = true
        binding.favoritesList.isVisible = false
    }

    private fun showError() {
        binding.imgError.setImageResource(R.drawable.no_jobs)
        binding.txtError.setText(R.string.unable_to_retrieve_job_listing)

        binding.imgError.isVisible = true
        binding.txtError.isVisible = true
        binding.favoritesList.isVisible = false
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
    }

}
