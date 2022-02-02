package br.com.douglasmotta.dogapichallenge.framework.network.response

import br.com.douglasmotta.dogapichallenge.domain.model.Breed
import com.google.gson.annotations.SerializedName

data class BreedSearchResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("breed_group")
    val group: String?,
    @SerializedName("origin")
    val origin: String?
)

fun BreedSearchResponse.toBreedModel() = Breed(
    id = this.id,
    name = this.name,
    group = this.group ?: "",
    origin = this.origin ?: ""
)
