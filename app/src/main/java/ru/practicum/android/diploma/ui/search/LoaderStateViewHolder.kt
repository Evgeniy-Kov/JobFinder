package ru.practicum.android.diploma.ui.search

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ViewProgressItemBinding

class LoaderStateViewHolder(
    val binding: ViewProgressItemBinding,
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            val context = binding.root.context
            Toast.makeText(context, context.getString(R.string.check_your_connection), Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}
