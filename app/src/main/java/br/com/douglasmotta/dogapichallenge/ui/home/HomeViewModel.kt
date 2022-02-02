package br.com.douglasmotta.dogapichallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchDogsUseCase: SearchDogsUseCase
) : ViewModel() {

    fun dogsPagingData(
        query: String = "",
        order: String
    ): Flow<PagingData<Dog>> {
        return searchDogsUseCase(
            SearchDogsUseCase.SearchDogParams(query, order, getPagingConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPagingConfig() = PagingConfig(
        pageSize = 20
    )
}