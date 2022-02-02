package br.com.douglasmotta.dogapichallenge.data

import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import br.com.douglasmotta.dogapichallenge.framework.network.response.BreedSearchResponse
import retrofit2.Response

interface DogRemoteDataSource {

    suspend fun fetchDogs(queries: Map<String, String>): Response<List<DogResponse>>

    suspend fun searchBreeds(querySearch: String): List<BreedSearchResponse>
}