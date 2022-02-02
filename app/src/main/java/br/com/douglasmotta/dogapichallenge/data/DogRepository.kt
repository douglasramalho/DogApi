package br.com.douglasmotta.dogapichallenge.data

import androidx.paging.PagingSource
import br.com.douglasmotta.dogapichallenge.domain.model.Breed
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.domain.model.QueryData

interface DogRepository {

    fun fetchDogs(queryData: QueryData): PagingSource<Int, Dog>

    suspend fun searchBreeds(querySearch: String): List<Breed>
}