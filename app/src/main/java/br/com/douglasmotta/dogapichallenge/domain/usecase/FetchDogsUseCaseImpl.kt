package br.com.douglasmotta.dogapichallenge.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.douglasmotta.dogapichallenge.data.DogRepository
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.domain.model.QueryData
import br.com.douglasmotta.dogapichallenge.domain.model.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchDogsUseCase {
    operator fun invoke(params: SearchDogParams): Flow<PagingData<Dog>>

    data class SearchDogParams(
        val query: String,
        val sort: String,
        val pagingConfig: PagingConfig
    )
}

class FetchDogsUseCaseImpl @Inject constructor(
    private val repository: DogRepository
) : FetchDogsUseCase, PagingUseCase<FetchDogsUseCase.SearchDogParams, Dog>() {

    override fun createFlowObservable(params: FetchDogsUseCase.SearchDogParams): Flow<PagingData<Dog>> {
        val sortType = when (params.sort) {
            "A-Z" -> SortType.ASC
            else -> SortType.DESC
        }

        val queryData = QueryData(params.query, sortType)

        val pagingSource = repository.fetchDogs(queryData)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }
}