package br.com.douglasmotta.dogapichallenge.framework.di

import br.com.douglasmotta.dogapichallenge.domain.usecase.FetchDogsUseCase
import br.com.douglasmotta.dogapichallenge.domain.usecase.FetchDogsUseCaseImpl
import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchUseCase
import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindFetchDogsUseCase(useCase: FetchDogsUseCaseImpl): FetchDogsUseCase

    @Binds
    fun bindSearchUseCase(useCase: SearchUseCaseImpl): SearchUseCase
}