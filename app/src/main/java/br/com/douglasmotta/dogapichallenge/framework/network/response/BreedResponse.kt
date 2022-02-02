package br.com.douglasmotta.dogapichallenge.framework.network.response

import br.com.douglasmotta.dogapichallenge.domain.model.Breed
import com.google.gson.annotations.SerializedName

data class BreedResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("bred_for")
    val origin: String?,
    @SerializedName("breed_group")
    val group: String?
)

fun BreedResponse.toBreedDomain() = Breed(
    name = this.name,
    origin = this.origin ?: "",
    group = this.group ?: ""
)
