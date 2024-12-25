package ru.practicum.android.diploma.ui.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.ui.search.VacancySearchFragment.Companion.TEXT_DEF
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel
import ru.practicum.android.diploma.util.debounce

class IndustryFragment : Fragment() {

    private var _binding: FragmentIndustryBinding? = null
    private val binding: FragmentIndustryBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)
    private var searchValue = TEXT_DEF
    private var searchAdapter = IndustryAdapter()
    private val selectedIndustries = hashSetOf<Industry>() // добавить потом все эти ранее выбранные отрасли в фильтр

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarIndustry.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        val onIndustryClickDebounce = debounce<Industry>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { industry ->
            onIndustryClick(industry)
        }

        searchAdapter.onItemClickListener = IndustryViewHolder.OnItemClickListener { industry ->
            onIndustryClickDebounce(industry)
        }

        viewModel.getIndustries()
        viewModel.industriesList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Error -> TODO()
                is Resource.Success -> {
                    searchAdapter.submitList(result.data)
                    binding.industryList.adapter = searchAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onIndustryClick(industry: Industry) {
        industry.isClicked = !industry.isClicked
        if (industry.isClicked) {
            selectedIndustries.add(industry)
        } else {
            selectedIndustries.remove(industry)
        }
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 300L
    }
}
