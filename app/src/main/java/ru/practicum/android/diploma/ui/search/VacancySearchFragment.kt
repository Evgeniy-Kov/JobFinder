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
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancySearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchFragment : Fragment() {

    private var _binding: FragmentVacancySearchBinding? = null
    private val binding: FragmentVacancySearchBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by koinNavGraphViewModel<VacancySearchViewModel>(R.id.vacancySearchFragment)
    private var searchValue = TEXT_DEF
    private var searchAdapter = VacancyAdapter()

    private var isLoadHasError = false

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

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLoadHasError) {
                    if (dy > 0) {
                        val pos =
                            (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val itemsCount = searchAdapter.itemCount
                        if (pos >= itemsCount - RETRY_INDENT) {
                            retryLoad()
                        }
                    }
                } else {
                    binding.recyclerView.removeOnScrollListener(this)
                }
            }
        }

        searchAdapter.onItemClickListener = VacancyViewHolder.OnItemClickListener { vacancy ->
            onVacancyClickDebounce(vacancy)
        }
        binding.recyclerView.adapter = searchAdapter.withLoadStateFooter(LoaderStateAdapter())

        initializeViews()

        observeLoadingData(onScrollListener)

        viewModel.itemCountLivedata.observe(viewLifecycleOwner) { count ->
            binding.valueSearchResultTv.text =
                String.format(getString(R.string.vacancies_found), count)
        }

        renderState(SearchScreenState.Default)
    }

    private fun observeLoadingData(listener: RecyclerView.OnScrollListener) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancies
                    .collectLatest {
                        searchAdapter.submitData(it)
                        searchAdapter.addLoadStateListener { state ->
                            processResult(searchAdapter.snapshot().size, state.refresh)
                            if (state.hasError) {
                                isLoadHasError = true
                                binding.recyclerView.addOnScrollListener(listener)
                            } else {
                                isLoadHasError = false
                            }
                        }
                    }
            }
        }
    }

    private fun retryLoad() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(RETRY_DEBOUNCE_DELAY)
            if (isLoadHasError) {
                searchAdapter.retry()
            }
        }
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
                renderState(SearchScreenState.Default)
            } else {
                viewModel.searchDebounce(searchValue)
            }

        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchDebounce(searchValue)
            }
            false
        }
    }

    private fun processResult(dataSize: Int, state: LoadState) {
        when (state) {
            is LoadState.Loading -> {
                renderState(SearchScreenState.Loading)
            }

            is LoadState.Error -> {
                renderState(SearchScreenState.Error(state.error.message ?: ""))
            }

            is LoadState.NotLoading -> {
                handleNotLoadingState(dataSize)
            }
        }
    }

    private fun handleNotLoadingState(dataSize: Int) {
        when {
            dataSize == 0 && searchValue.isBlank() -> {
                renderState(SearchScreenState.Default)
            }

            dataSize == 0 && searchValue.isNotBlank() -> {
                renderState(SearchScreenState.NothingFound)
            }

            else -> {
                renderState(SearchScreenState.Content)
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

    private fun renderState(state: SearchScreenState) {
        binding.progressBar.isVisible = state is SearchScreenState.Loading
        binding.recyclerView.isVisible = state is SearchScreenState.Content
        binding.placeholderIv.isVisible = state !is SearchScreenState.Content && state !is SearchScreenState.Loading
        binding.placeholderTv.isVisible = state !is SearchScreenState.Content && state !is SearchScreenState.Loading
        binding.valueSearchResultTv.isVisible =
            state is SearchScreenState.Content || state is SearchScreenState.NothingFound
        if (state !is SearchScreenState.Content && state !is SearchScreenState.Loading) {
            setMessagesAndDrawable(state)
        }
    }

    private fun setMessagesAndDrawable(state: SearchScreenState) {
        val message = getPlaceholderMessage(state)
        val drawableResId = getPlaceholderDrawableResId(state)
        binding.placeholderIv.setImageResource(drawableResId)
        binding.placeholderTv.text = message
        if (state is SearchScreenState.NothingFound) {
            binding.valueSearchResultTv.text = requireContext().getString(R.string.no_such_jobs)
        }
    }

    private fun getPlaceholderMessage(state: SearchScreenState): String {
        return when (state) {
            is SearchScreenState.NothingFound -> requireContext().getString(R.string.unable_to_retrieve_job_listing)
            is SearchScreenState.Error -> getPlaceholderErrorMessage(state.errorMessage)
            else -> ""
        }
    }

    private fun getPlaceholderDrawableResId(state: SearchScreenState): Int {
        return when (state) {
            is SearchScreenState.NothingFound -> R.drawable.no_jobs
            is SearchScreenState.Error -> getPlaceholderErrorDrawableResId(state.errorMessage)
            else -> R.drawable.start_search_image
        }
    }

    private fun getPlaceholderErrorMessage(errorCode: String): String {
        val stringResId = if (errorCode == "-1") R.string.no_internet else R.string.server_error
        return requireContext().getString(stringResId)
    }

    private fun getPlaceholderErrorDrawableResId(errorCode: String): Int {
        return if (errorCode == "-1") R.drawable.no_internet else R.drawable.server_error

    }

    companion object {
        const val TEXT_DEF = ""
        const val CLICK_DEBOUNCE_DELAY = 1000L
        const val RETRY_DEBOUNCE_DELAY = 2000L
        const val RETRY_INDENT = 3
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}
