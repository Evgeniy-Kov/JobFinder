package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.practicum.android.diploma.databinding.ViewProgressItemBinding

class LoaderStateAdapter : LoadStateAdapter<LoaderStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoaderStateViewHolder(
            ViewProgressItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LoaderStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}
