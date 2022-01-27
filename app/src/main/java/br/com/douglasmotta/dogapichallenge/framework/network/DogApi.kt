package br.com.douglasmotta.dogapichallenge.framework.network

import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DogApi {

    @GET("images/search")
    suspend fun searchDogs(
        @QueryMap
        queries: Map<String, String>
    ): List<DogResponse>
}