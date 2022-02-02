package br.com.douglasmotta.dogapichallenge.domain.model

data class QueryData(
    val query: String = "",
    val sort: SortType = SortType.ASC
)

enum class SortType {
    ASC, DESC
}
