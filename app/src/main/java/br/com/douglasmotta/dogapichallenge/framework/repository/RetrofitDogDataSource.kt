package br.com.douglasmotta.dogapichallenge.framework.repository

import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.framework.network.DogApi
import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import br.com.douglasmotta.dogapichallenge.framework.network.response.BreedSearchResponse
import retrofit2.Response
import javax.inject.Inject

class RetrofitDogDataSource @Inject constructor(
    private val dogApi: DogApi
) : DogRemoteDataSource {

    override suspend fun fetchDogs(queries: Map<String, String>): Response<List<DogResponse>> {
        return dogApi.fetchDogs(queries)
    }

    override suspend fun searchBreeds(querySearch: String): List<BreedSearchResponse> {
        return dogApi.searchBreeds(querySearch)
    }
}