package br.com.douglasmotta.dogapichallenge.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.douglasmotta.dogapichallenge.data.DogRepository
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchDogsUseCase {
    operator fun invoke(params: SearchDogParams): Flow<PagingData<Dog>>

    data class SearchDogParams(val query: String, val pagingConfig: PagingConfig)
}

class SearchDogsUseCaseImpl @Inject constructor(
    private val repository: DogRepository
) : SearchDogsUseCase, PagingUseCase<SearchDogsUseCase.SearchDogParams, Dog>() {

    override fun createFlowObservable(params: SearchDogsUseCase.SearchDogParams): Flow<PagingData<Dog>> {
        val pagingSource = repository.searchDogs(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }
}