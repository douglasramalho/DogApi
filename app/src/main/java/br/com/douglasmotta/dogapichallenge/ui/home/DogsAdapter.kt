package br.com.douglasmotta.dogapichallenge.ui.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.douglasmotta.dogapichallenge.domain.model.Dog

class DogsAdapter : PagingDataAdapter<Dog, DogsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        return DogsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(
                oldItem: Dog,
                newItem: Dog
            ): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(
                oldItem: Dog,
                newItem: Dog
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}