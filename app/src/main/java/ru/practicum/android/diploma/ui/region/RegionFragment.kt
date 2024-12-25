package ru.practicum.android.diploma.ui.region

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
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

class RegionFragment : Fragment() {

    private var _binding: FragmentRegionBinding? = null
    private val binding: FragmentRegionBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val areaAdapter = AreaAdapter()

    private val args by navArgs<RegionFragmentArgs>()

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.regionList.adapter = areaAdapter

        setScreenMode()

        viewModel.areaScreenState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.regionEditText.doOnTextChanged { text, _, _, _ ->
            clearButtonVisibility(text, binding.clearButton)
            if (!text.isNullOrBlank()) {
                viewModel.setRegionNameFilter(text.toString())
            }
        }

        binding.clearButton.setOnClickListener {
            binding.regionEditText.setText("")
        }

        binding.regionList.itemAnimator = null

        binding.toolbarRegion.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.regionNameFilter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchQuery)
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.regionEditText) {
            val queryText = text?.toString() ?: ""
            if (queryText != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    private fun renderState(state: AreaScreenState) {
        binding.progressBar.isVisible = state is AreaScreenState.Loading
        binding.regionList.isVisible = state is AreaScreenState.Content
        binding.imgError.isVisible = state !is AreaScreenState.Content && state !is AreaScreenState.Loading
        binding.txtError.isVisible = state !is AreaScreenState.Content && state !is AreaScreenState.Loading
        if (state !is AreaScreenState.Content && state !is AreaScreenState.Loading) {
            setMessagesAndDrawable()
        }
    }

    private fun setMessagesAndDrawable() {
        val message = requireContext().getString(R.string.not_get_list)
        val drawableResId = R.drawable.region_error_placeholder
        binding.imgError.setImageResource(drawableResId)
        binding.txtError.text = message
    }

    private fun setScreenMode() {
        if (args.isRegionMode) {
            setRegionScreenMode()
        } else {
            setCountryScreenMode()
        }
    }

    private fun setCountryScreenMode() {
        binding.groupSearchField.isVisible = args.isRegionMode
        viewModel.getAreas()
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            areaAdapter.submitList(countries)
        }
        areaAdapter.onItemClickListener = AreaViewHolder.OnItemClickListener { country ->
            viewModel.setCountryId(country.id)
            findNavController().navigateUp()
        }
    }

    private fun setRegionScreenMode() {
        binding.groupSearchField.isVisible = args.isRegionMode
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.regions.collect { regions ->
                areaAdapter.submitList(regions)
            }
        }
        areaAdapter.onItemClickListener = AreaViewHolder.OnItemClickListener {
        }
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
