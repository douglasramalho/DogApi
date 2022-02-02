package br.com.douglasmotta.dogapichallenge.ui.search

import androidx.lifecycle.ViewModel
import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    fun searchBreeds(
        querySearch: String = ""
    ) = searchUseCase(SearchUseCase.SearchDogParams(querySearch))
}