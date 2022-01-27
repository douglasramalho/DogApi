package br.com.douglasmotta.dogapichallenge.framework.di

import br.com.douglasmotta.dogapichallenge.BuildConfig
import br.com.douglasmotta.dogapichallenge.framework.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}