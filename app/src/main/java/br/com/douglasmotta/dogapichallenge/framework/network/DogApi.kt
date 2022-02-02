package br.com.douglasmotta.dogapichallenge.framework.network

import br.com.douglasmotta.dogapichallenge.framework.network.response.DogResponse
import br.com.douglasmotta.dogapichallenge.framework.network.response.BreedSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DogApi {

    @GET("images/search")
    suspend fun fetchDogs(
        @QueryMap
        queries: Map<String, String>
    ): List<DogResponse>

    @GET("breeds/search")
    suspend fun searchBreeds(
        @Query("q")
        searchQuery: String
    ): List<BreedSearchResponse>
}