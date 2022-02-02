package br.com.douglasmotta.dogapichallenge.domain.model

data class Breed(
    val id: String = "",
    val name: String = "Unspecified",
    val origin: String = "",
    val group: String = "",
    val temperament: String = ""
)
