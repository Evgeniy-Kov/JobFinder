package ru.practicum.android.diploma.ui.placeofwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

class PlaceOfWorkFragment : Fragment() {

    private var _binding: FragmentPlaceOfWorkBinding? = null
    private val binding: FragmentPlaceOfWorkBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryEnter.setOnClickListener {
            val direction =
                PlaceOfWorkFragmentDirections.actionPlaceOfWorkFragmentToRegionFragment(isRegionMode = false)
            findNavController().navigate(direction)
        }

        binding.regionEnter.setOnClickListener {
            val direction = PlaceOfWorkFragmentDirections.actionPlaceOfWorkFragmentToRegionFragment(isRegionMode = true)
            findNavController().navigate(direction)
        }

        binding.toolbarPlaceOfWork.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getAreas()

        viewModel.chosenCountry.observe(viewLifecycleOwner) { country ->
            binding.countryEnter.setText(country?.name)
        }

        viewModel.chosenRegion.observe(viewLifecycleOwner) { region ->
            binding.regionEnter.setText(region?.name)
        }

        binding.regionEnter.doOnTextChanged { s, _, _, _ ->
            acceptButtonVisibility(!s.isNullOrBlank())
        }

        binding.countryEnter.doOnTextChanged { s, _, _, _ ->
            acceptButtonVisibility(!s.isNullOrBlank())
        }

        binding.acceptButton.setOnClickListener {
            viewModel.setPlaceOfWork()
            findNavController().navigateUp()
        }
    }

    private fun acceptButtonVisibility(
        visibility: Boolean,
    ) {
        when (visibility) {
            true -> {
                binding.acceptButton.visibility = VISIBLE
            }

            false -> {
                binding.acceptButton.visibility = GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
