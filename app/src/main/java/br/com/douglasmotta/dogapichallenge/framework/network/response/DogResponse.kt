package br.com.douglasmotta.dogapichallenge.framework.network.response

import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("breeds")
    val breeds: List<BreedResponse>
)

fun DogResponse.toDogDomain() = Dog(
    id = this.id,
    imageUrl = this.imageUrl,
    breeds = this.breeds.map { it.toBreedDomain() }
)
