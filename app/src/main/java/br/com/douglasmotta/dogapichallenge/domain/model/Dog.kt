package br.com.douglasmotta.dogapichallenge.domain.model

data class Dog(
    val id: String,
    val imageUrl: String,
    val breeds: List<Breed>
)