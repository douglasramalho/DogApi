package br.com.douglasmotta.dogapichallenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.douglasmotta.dogapichallenge.databinding.ItemDogLoadMoreBinding

class DogsLoadStateViewHolder(
    itemBinding: ItemDogLoadMoreBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val progressBarLoadingMore = itemBinding.progressLoadingMore
    private val textTryAgainMessage = itemBinding.textTryAgain.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadSate: LoadState) {
        progressBarLoadingMore.isVisible = loadSate is LoadState.Loading
        textTryAgainMessage.isVisible = loadSate is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): DogsLoadStateViewHolder {
            val itemBinding = ItemDogLoadMoreBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return DogsLoadStateViewHolder(itemBinding, retry)
        }
    }
}