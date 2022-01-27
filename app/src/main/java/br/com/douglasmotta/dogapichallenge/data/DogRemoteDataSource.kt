package br.com.douglasmotta.dogapichallenge.data

import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse

interface DogRemoteDataSource {

    suspend fun searchDogs(queries: Map<String, String>): List<DogResponse>
}