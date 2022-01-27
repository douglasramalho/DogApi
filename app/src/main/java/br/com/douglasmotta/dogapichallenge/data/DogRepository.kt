package br.com.douglasmotta.dogapichallenge.data

import androidx.paging.PagingSource
import br.com.douglasmotta.dogapichallenge.domain.model.Dog

interface DogRepository {

    fun searchDogs(query: String): PagingSource<Int, Dog>
}