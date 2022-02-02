package br.com.douglasmotta.dogapichallenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.douglasmotta.dogapichallenge.R
import br.com.douglasmotta.dogapichallenge.databinding.ItemDogBinding
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import com.bumptech.glide.Glide

class DogsViewHolder(
    itemCharacterBinding: ItemDogBinding
) : RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val imageDog = itemCharacterBinding.imageDog
    private val textName = itemCharacterBinding.textName

    fun bind(dog: Dog) {
        Glide.with(itemView)
            .load(dog.imageUrl)
            .fallback(R.drawable.ic_img_loading_error)
            .error(R.drawable.ic_img_loading_error)
            .into(imageDog)

        textName.text = dog.breed.name
    }

    companion object {
        fun create(parent: ViewGroup): DogsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemDogBinding.inflate(inflater, parent, false)
            return DogsViewHolder(itemBinding)
        }
    }
}