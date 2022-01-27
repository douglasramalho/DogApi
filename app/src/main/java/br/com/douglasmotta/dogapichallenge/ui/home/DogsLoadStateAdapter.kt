package br.com.douglasmotta.dogapichallenge.ui.home

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class DogsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<DogsLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = DogsLoadStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: DogsLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}