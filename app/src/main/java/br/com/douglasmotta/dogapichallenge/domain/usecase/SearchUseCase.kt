package br.com.douglasmotta.dogapichallenge.domain.usecase

import br.com.douglasmotta.dogapichallenge.data.DogRepository
import br.com.douglasmotta.dogapichallenge.domain.ResultStatus
import br.com.douglasmotta.dogapichallenge.domain.model.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchUseCase {
    operator fun invoke(params: SearchDogParams): Flow<ResultStatus<List<Breed>>>

    data class SearchDogParams(
        val searchQuery: String
    )
}

class SearchUseCaseImpl @Inject constructor(
    private val dogRepository: DogRepository
) : SearchUseCase, UseCase<SearchUseCase.SearchDogParams, Breed>() {

    override suspend fun doWork(params: SearchUseCase.SearchDogParams): ResultStatus<List<Breed>> {
        val breeds = dogRepository.searchBreeds(params.searchQuery)
        return ResultStatus.Success(breeds)
    }
}