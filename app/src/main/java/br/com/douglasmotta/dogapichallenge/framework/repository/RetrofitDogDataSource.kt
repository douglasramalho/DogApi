package br.com.douglasmotta.dogapichallenge.framework.repository

import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.framework.network.DogApi
import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import javax.inject.Inject

class RetrofitDogDataSource @Inject constructor(
    private val dogApi: DogApi
) : DogRemoteDataSource {

    override suspend fun searchDogs(queries: Map<String, String>): List<DogResponse> {
        return dogApi.searchDogs(queries)
    }
}