package br.com.douglasmotta.dogapichallenge.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.douglasmotta.dogapichallenge.databinding.ItemBreedBinding
import br.com.douglasmotta.dogapichallenge.domain.model.Breed

class SearchAdapter : ListAdapter<Breed, SearchAdapter.SearchViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchViewHolder(binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root) {

        private val textName = binding.textName
        private val textGroup = binding.textGroup
        private val textOrigin = binding.textOrigin

        fun bind(breed: Breed) {
            textName.text = breed.name
            textGroup.text = breed.group
            textOrigin.text = breed.origin
        }

        companion object {
            fun create(parent: ViewGroup): SearchViewHolder {
                val binding = ItemBreedBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return SearchViewHolder(binding)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Breed>() {
            override fun areItemsTheSame(oldItem: Breed, newItem: Breed) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Breed, newItem: Breed) = oldItem == newItem

        }
    }
}