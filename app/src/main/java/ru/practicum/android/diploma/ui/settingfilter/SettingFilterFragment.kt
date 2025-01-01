package ru.practicum.android.diploma.ui.settingfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFilterBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

class SettingFilterFragment : Fragment() {

    private var _binding: FragmentSettingFilterBinding? = null
    private val binding: FragmentSettingFilterBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)
    private var oldSalary = ""

    private var currentFilterSettings = Filter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.salaryFrame.requestFocus()

        binding.placeOfWorkEnter.setOnClickListener {
            val directions = SettingFilterFragmentDirections.actionSettingFilterFragmentToPlaceOfWorkFragment()
            findNavController().navigate(directions)
        }

        binding.industryEnter.setOnClickListener {
            val directions = SettingFilterFragmentDirections.actionSettingFilterFragmentToIndustryFragment()
            findNavController().navigate(directions)
        }

        binding.toolbarFilter.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getIndustries()

        binding.resetButton.setOnClickListener {
            viewModel.clearFilter()
        }
        binding.acceptButton.setOnClickListener {
            viewModel.saveFilter()
            findNavController().navigateUp()
        }
        viewModel.currentFilter.observe(viewLifecycleOwner) { filter ->
            currentFilterSettings = filter
            processFilterResult(filter)
        }

        binding.salaryEnter.doOnTextChanged { s, _, _, _ ->
            setButtonsVisibility(currentFilterSettings)
            if (s?.isBlank() == false) {
                binding.salaryFrame.endIconMode = END_ICON_CLEAR_TEXT
                binding.salaryFrame.setEndIconDrawable(R.drawable.ic_clear)
                oldSalary = s.toString()
                viewModel.setSalary(Integer.parseInt(s?.toString() ?: "0"))
            } else {
                binding.salaryFrame.endIconMode = END_ICON_NONE
                binding.salaryFrame.endIconDrawable = null
            }
        }

        binding.withoutSalary.setOnClickListener {
            viewModel.setOnlyWithSalary(binding.withoutSalary.isChecked)
        }
    }

    private fun setButtonsVisibility(filter: Filter) {
        val savedFilter = viewModel.preferenceUpdates.value ?: Filter()
        binding.resetButton.isVisible = !filter.isDefault
        binding.acceptButton.isVisible = filter != savedFilter
    }

    private fun processFilterResult(filter: Filter) {
        setButtonsVisibility(filter)
        if (!filter.isDefault) {
            processArea(filter.country, filter.region)
            binding.industryEnter.setText(filter.industry?.name ?: "")
            binding.withoutSalary.isChecked = filter.onlyWithSalary
            val newSalary = filter.salary?.toString() ?: ""
            if (newSalary != oldSalary) {
                binding.salaryEnter.setText(newSalary)
            }
        } else {
            binding.placeOfWorkEnter.text = null
            binding.industryEnter.text = null
            binding.withoutSalary.isChecked = false
            binding.salaryEnter.text = null
        }
        setCheckedIcon(filter.onlyWithSalary)
    }

    private fun processArea(country: Country?, region: Region?) {
        var countryName = ""
        var regionName = ""
        if (!country?.name.isNullOrBlank()) {
            countryName = country!!.name
        }
        if (!region?.name.isNullOrBlank()) {
            regionName = region!!.name
        }
        binding.placeOfWorkEnter.setText(
            getString(
                R.string.filter_place_of_work,
                countryName,
                regionName
            )
        )
    }

    fun setCheckedIcon(isChecked: Boolean) {
        if (isChecked) {
            binding.withoutSalary.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_box_on)
        } else {
            binding.withoutSalary.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_box_off)
        }
    }
}
