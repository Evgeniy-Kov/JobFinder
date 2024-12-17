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
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancySearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchFragment : Fragment() {

    private var _binding: FragmentVacancySearchBinding? = null
    private val binding: FragmentVacancySearchBinding
        get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by viewModel<VacancySearchViewModel>()
    private var searchValue = TEXT_DEF
    private var debounceBoolean = true
    private lateinit var onJobClickDebounce: (Unit) -> Unit
    private var searchAdapter: JobListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onJobClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false
        ) { debounceBoolean = true }

        val onItemClickListener = OnItemClickListener { item ->
            if (debounceBoolean) {
                debounceBoolean = false
                openVacancy(item)
                onJobClickDebounce(Unit)
            }
        }
        searchAdapter = JobListAdapter(onItemClickListener)
        binding.recyclerView.adapter = searchAdapter

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
            clearSearchAdapter()
        }

        binding.searchEditText.requestFocus()
        binding.searchEditText.setText(searchValue)

        binding.searchEditText.doOnTextChanged { s, _, _, _ ->
            clearButtonVisibility(s, binding.clearButton)
            searchValue = s.toString()
            if (binding.searchEditText.hasFocus() && s?.isEmpty() == true) {
                showDefaultContent()
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

//        viewModel.observeSearchState().observe(viewLifecycleOwner) { state ->
//            renderState(state)
//        }

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

    private fun clearSearchAdapter() {
        viewModel.stopSearch()
        searchAdapter?.submitList(mutableListOf())
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

    private fun openVacancy(vacancy: Vacancy) {

    }

    private fun renderState(state: SearchState) {
        when (state) {
            // добавить потом стейт нет интернета
            is SearchState.ContentSearch -> updateContentSearch(state.jobs)
            is SearchState.Error -> showErrorMessage(state.errorMessage)
            is SearchState.Loading -> showLoading(true)
            is SearchState.NothingFound -> showEmptyView()
            is SearchState.Default -> showDefaultContent()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showDefaultContent() {
        showLoading(false)
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.serverErrorIv.isVisible = false
        binding.serverErrorTv.isVisible = false
        binding.noResultSearchIv.isVisible = false
        binding.noResultSearchTv.isVisible = false
        binding.valueSearchResultTv.isVisible = false
        binding.rvProgressBar.isVisible = false
        binding.startIv.isVisible = true
        clearSearchAdapter()
    }

    private fun showContentSearch(jobsValue: Int) {
        binding.recyclerView.isVisible = true
        binding.valueSearchResultTv.text = String.format(getString(R.string.vacancies_found), jobsValue)
        binding.valueSearchResultTv.isVisible = true
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.serverErrorIv.isVisible = false
        binding.serverErrorTv.isVisible = false
        binding.noResultSearchIv.isVisible = false
        binding.noResultSearchTv.isVisible = false
        binding.rvProgressBar.isVisible = false // переделать потом на постраничную загрузку
        binding.startIv.isVisible = false
    }

    private fun updateContentSearch(jobs: List<Vacancy>) {
        showLoading(false)
        searchAdapter?.submitList(jobs)
        //   searchAdapter?.itemCount?.let { searchAdapter?.notifyItemRangeChanged(0, it) }
        showContentSearch(jobs.size)
    }

    private fun showEmptyView() {
        showLoading(false)
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.serverErrorIv.isVisible = false
        binding.serverErrorTv.isVisible = false
        binding.noResultSearchIv.isVisible = true
        binding.noResultSearchTv.isVisible = true
        binding.valueSearchResultTv.text = getString(R.string.no_such_jobs)
        binding.valueSearchResultTv.isVisible = true
        binding.rvProgressBar.isVisible = false
        binding.startIv.isVisible = false
        clearSearchAdapter()
    }

    private fun showErrorMessage(additionalMessage: String) {
        showLoading(false)
        binding.recyclerView.isVisible = false
        binding.noInternetIv.isVisible = false
        binding.noInternetTv.isVisible = false
        binding.serverErrorIv.isVisible = true
        binding.serverErrorTv.isVisible = true
        binding.noResultSearchIv.isVisible = false
        binding.noResultSearchTv.isVisible = false
        binding.valueSearchResultTv.isVisible = false
        binding.rvProgressBar.isVisible = false
        binding.startIv.isVisible = false
        clearSearchAdapter()
        if (additionalMessage.isNotEmpty()) {
            Toast.makeText(context, additionalMessage, Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        const val TEXT_DEF = ""
        const val CLICK_DEBOUNCE_DELAY = 1000L
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

}
