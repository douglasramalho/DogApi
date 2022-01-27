package br.com.douglasmotta.dogapichallenge.framework.repository

import androidx.paging.PagingSource
import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.data.DogRepository
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.framework.paging.DogPagingSource
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val remoteDataSource: DogRemoteDataSource
) : DogRepository {

    override fun searchDogs(query: String): PagingSource<Int, Dog> {
        return DogPagingSource(remoteDataSource, query)
    }
}