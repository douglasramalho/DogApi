package br.com.douglasmotta.dogapichallenge.framework.di

import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchDogsUseCase
import br.com.douglasmotta.dogapichallenge.domain.usecase.SearchDogsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSearchDogsUseCase(useCase: SearchDogsUseCaseImpl): SearchDogsUseCase
}