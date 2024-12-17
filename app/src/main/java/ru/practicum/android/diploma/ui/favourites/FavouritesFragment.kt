package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.FavoritesState
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.adapters.VacancyAdapter
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoritesViewModel>()
    private var adapter: VacancyAdapter? = null

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStateLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesState.Content -> showContent(state.vacanciesList)
                is FavoritesState.Empty -> showEmptyMessage()
                is FavoritesState.Error -> showError()

            }
        }

        val onVacancyClickDebounce = debounce<Vacancy>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        )
        //{ vacancy ->
        //launchVacancyDetails(vacancy)
        //}

        adapter = VacancyAdapter { vacancy -> onVacancyClickDebounce(vacancy) }
        binding.favoritesList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFavoritesList()
    }

    private fun showContent(listOfVacancies: List<Vacancy>) {
        adapter?.setData(listOfVacancies)
        binding.imgError.isVisible = false
        binding.txtError.isVisible = false
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = true
    }

    private fun showEmptyMessage() {
        binding.imgError.setImageResource(R.drawable.empty_favorites)
        binding.txtError.setText(R.string.empty_favorites)

        binding.imgError.isVisible = true
        binding.txtError.isVisible = true
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = false
    }

    private fun showError() {
        binding.imgError.setImageResource(R.drawable.no_jobs)
        binding.txtError.setText(R.string.unable_to_retrieve_job_listing)

        binding.imgError.isVisible = true
        binding.txtError.isVisible = true
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = false
    }

    // необходимо сделать ссылку на экран описания вакансии при нажатии на вакансию из списка изранного

    //private fun launchVacancyDetails(vacancy: Vacancy) {
    //   findNavController().navigate(
    //       R.id.,
    //       VacancyDetailsFragment.createArgs(vacancy = vacancy, vacancyNeedUpdate = false)
    // )
    //
}
