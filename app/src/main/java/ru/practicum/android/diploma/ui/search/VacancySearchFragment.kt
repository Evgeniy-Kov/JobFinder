package ru.practicum.android.diploma.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancySearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.isInternetAvailable

class VacancySearchFragment : Fragment() {

    private var _binding: FragmentVacancySearchBinding? = null
    private val binding: FragmentVacancySearchBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)
    private var searchValue = TEXT_DEF
    private var searchAdapter = VacancyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onVacancyClickDebounce = debounce<Vacancy>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            onVacancyClick(vacancy)
        }

        searchAdapter.onItemClickListener = VacancyViewHolder.OnItemClickListener { vacancy ->
            onVacancyClickDebounce(vacancy)
        }
        binding.recyclerView.adapter = searchAdapter.withLoadStateFooter(LoaderStateAdapter())

        initializeViews()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancies
                    .collectLatest {
                        searchAdapter.submitData(it)
                        searchAdapter.addLoadStateListener { state ->
                            processResult(searchAdapter.snapshot().size, state.refresh)
                        }
                    }
            }
        }

        viewModel.itemCountLivedata.observe(viewLifecycleOwner) { count ->
            binding.valueSearchResultTv.text =
                String.format(getString(R.string.vacancies_found), count)
        }

        processResult(searchAdapter.snapshot().size, LoadState.NotLoading(false))
    }

    private fun initializeViews() {
        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
            clearSearchAdapter()
            viewModel.clearLatestSearchText()
        }

        binding.searchEditText.requestFocus()
        binding.searchEditText.setText(searchValue)

        binding.searchEditText.doOnTextChanged { s, _, _, _ ->
            clearButtonVisibility(s, binding.clearButton)
            searchValue = s.toString().trim()
            if (binding.searchEditText.hasFocus() && s?.isEmpty() == true) {
                showDefaultContent()
            } else {
                if (isInternetAvailable(requireContext())) {
                    viewModel.searchDebounce(searchValue)
                } else {
                    showErrorPlaceholder(R.string.no_internet, R.drawable.no_internet)
                }
            }

        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isInternetAvailable(requireContext())) {
                    viewModel.searchDebounce(searchValue)
                } else {
                    showErrorPlaceholder(R.string.no_internet, R.drawable.no_internet)
                }
            }
            false
        }
    }

    private fun processResult(dataSize: Int, state: LoadState) {
        _binding ?: return
        when (state) {
            is LoadState.Loading -> {
                showLoading()
            }

            is LoadState.Error -> {
                showErrorPlaceholder(R.string.server_error, R.drawable.server_error)
            }

            is LoadState.NotLoading -> {
                handleNotLoadingState(dataSize)
            }
        }
    }

    private fun handleNotLoadingState(dataSize: Int) {
        when {
            dataSize == 0 && searchValue.isBlank() -> {
                showDefaultContent()
            }

            dataSize == 0 && searchValue.isNotBlank() -> {
                showEmptyView()
            }

            else -> {
                showContentSearch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchValue)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            searchValue = savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF)
        }
    }

    private fun onVacancyClick(vacancy: Vacancy) {
        val direction =
            VacancySearchFragmentDirections.actionVacancySearchFragmentToVacancyFragment(vacancy.id)
        findNavController().navigate(direction)
    }

    private fun clearSearchAdapter() {
        viewModel.stopSearch()
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

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.valueSearchResultTv.isVisible = false
        binding.startIv.isVisible = false
        viewModel.stopSearch()
    }

    private fun showDefaultContent() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.valueSearchResultTv.isVisible = false
        binding.startIv.isVisible = true
        viewModel.stopSearch()
    }

    private fun showErrorPlaceholder(
        @StringRes stringResId: Int,
        @DrawableRes imageResId: Int
    ) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = true
        binding.noInternetTv.isVisible = true
        binding.noInternetIv.setImageResource(imageResId)
        binding.noInternetTv.text = requireContext().getString(stringResId)
        binding.valueSearchResultTv.isVisible = false
        binding.startIv.isVisible = false
        viewModel.stopSearch()
    }

    private fun showContentSearch() {
        binding.recyclerView.isVisible = true
        binding.valueSearchResultTv.isVisible = true
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.progressBar.isVisible = false
        binding.startIv.isVisible = false
    }

    private fun showEmptyView() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = true
        binding.noInternetTv.isVisible = true
        binding.noInternetIv.setImageResource(R.drawable.no_jobs)
        binding.noInternetTv.text = requireContext().getString(R.string.unable_to_retrieve_job_listing)
        binding.valueSearchResultTv.text = getString(R.string.no_such_jobs)
        binding.valueSearchResultTv.isVisible = true
        binding.startIv.isVisible = false
        viewModel.stopSearch()
    }

    companion object {
        const val TEXT_DEF = ""
        const val CLICK_DEBOUNCE_DELAY = 1000L
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}
