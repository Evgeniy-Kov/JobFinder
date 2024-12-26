package ru.practicum.android.diploma.ui.settingfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFilterBinding
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

class SettingFilterFragment : Fragment() {

    private var _binding: FragmentSettingFilterBinding? = null
    private val binding: FragmentSettingFilterBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)

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

        binding.placeOfWorkEnter.setOnClickListener {
            findNavController().navigate(R.id.placeOfWorkFragment)
        }

        binding.industryEnter.setOnClickListener {
            findNavController().navigate(R.id.industryFragment)
        }

        binding.toolbarFilter.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getIndustries()
        binding.resetButton.setOnClickListener {
            // viewModel.resetFilter()
        }
        binding.acceptButton.setOnClickListener {
            // viewModel.applyFilter()
        }

        binding.salaryEnter.doOnTextChanged { s, _, _, _ ->
            if (s?.isNotEmpty() == true) {
                binding.salaryFrame.endIconMode = END_ICON_CLEAR_TEXT
                binding.salaryFrame.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_clear)
                setButtonsVisibility(VISIBLE)
            } else {
                binding.salaryFrame.endIconMode = END_ICON_NONE
                binding.salaryFrame.endIconDrawable = null
                // не забыть убрать
                setButtonsVisibility(GONE)
            }
        }
    }

    private fun setButtonsVisibility(visibility: Int) {
        binding.resetButton.visibility = visibility
        binding.acceptButton.visibility = visibility
    }
}
