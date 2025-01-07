package ru.practicum.android.diploma.ui.industry

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

class IndustryFragment : Fragment() {

    private var _binding: FragmentIndustryBinding? = null
    private val binding: FragmentIndustryBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)
    private var searchAdapter = IndustryAdapter { selectedItem ->
        selectedIndustry = selectedItem
        binding.chooseButton.isVisible = true
    }
    private var selectedIndustry: Industry? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarIndustry.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.industryList.adapter = searchAdapter

        binding.industryEditText.doOnTextChanged { text, _, _, _ ->
            clearButtonVisibility(text, binding.clearButton)
            if (!text.isNullOrBlank()) {
                viewModel.setIndustryNameFilter(text.toString())
            }
        }

        binding.clearButton.setOnClickListener {
            renderState(IndustryScreenState.Content)
            binding.industryEditText.setText("")
            viewModel.setIndustryNameFilter("")
        }

        viewModel.industryNameFilter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchQuery)
            .launchIn(lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.industry.collect { items ->
                searchAdapter.industryList = items
            }
        }

        viewModel.industryScreenState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.chooseButton.setOnClickListener {
        }
    }

    private fun renderState(state: IndustryScreenState) {
        binding.progressBar.isVisible = state is IndustryScreenState.Loading
        binding.industryList.isVisible = state is IndustryScreenState.Content
        binding.imgError.isVisible = state !is IndustryScreenState.Content && state !is IndustryScreenState.Loading
        binding.txtError.isVisible = state !is IndustryScreenState.Content && state !is IndustryScreenState.Loading
        if (state !is IndustryScreenState.Content && state !is IndustryScreenState.Loading) {
            setMessagesAndDrawable(state)
        }
        if (state !is IndustryScreenState.Content && state !is IndustryScreenState.Loading) {
            setMessagesAndDrawable(state)
        }
    }

    private fun setMessagesAndDrawable(state: IndustryScreenState) {
        val message = getPlaceholderMessage(state)
        val drawableResId = getPlaceholderDrawableResId(state)
        binding.imgError.setImageResource(drawableResId)
        binding.txtError.text = message
    }

    private fun getPlaceholderMessage(state: IndustryScreenState): String {
        return when (state) {
            is IndustryScreenState.Empty -> requireContext().getString(R.string.no_such_industry)
            is IndustryScreenState.Error -> requireContext().getString(R.string.not_get_list)
            else -> ""
        }
    }

    private fun getPlaceholderDrawableResId(state: IndustryScreenState): Int {
        return when (state) {
            is IndustryScreenState.Empty -> R.drawable.no_jobs
            else -> R.drawable.region_error_placeholder
        }
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.industryEditText) {
            val queryText = text?.toString() ?: ""
            if (queryText != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisibility(s: CharSequence?, v: ImageView) {
        if (s.isNullOrEmpty()) {
            v.setImageResource(R.drawable.ic_search)
            v.isEnabled = false
            view?.let { activity?.hideKeyboard() }
        } else {
            v.setImageResource(R.drawable.ic_clear)
            v.isEnabled = true
        }
    }

    private fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
