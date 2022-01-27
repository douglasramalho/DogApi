package br.com.douglasmotta.dogapichallenge.framework.di

import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.data.DogRepository
import br.com.douglasmotta.dogapichallenge.framework.repository.DogRepositoryImpl
import br.com.douglasmotta.dogapichallenge.framework.repository.RetrofitDogDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindDogRepository(repository: DogRepositoryImpl): DogRepository

    @Binds
    fun bindRemoteDataSource(
        dataSource: RetrofitDogDataSource
    ): DogRemoteDataSource
}