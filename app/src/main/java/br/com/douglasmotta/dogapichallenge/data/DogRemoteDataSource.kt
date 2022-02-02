package br.com.douglasmotta.dogapichallenge.data

import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import br.com.douglasmotta.dogapichallenge.framework.network.response.BreedSearchResponse

interface DogRemoteDataSource {

    suspend fun fetchDogs(queries: Map<String, String>): List<DogResponse>

    suspend fun searchBreeds(querySearch: String): List<BreedSearchResponse>
}