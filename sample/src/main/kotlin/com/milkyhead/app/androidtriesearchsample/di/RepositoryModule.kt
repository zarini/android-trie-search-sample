package com.milkyhead.app.androidtriesearchsample.di

import com.milkyhead.app.androidtriesearchsample.data.datasource.local.DefaultFilePersonLoader
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.FilePersonLoader
import com.milkyhead.app.androidtriesearchsample.data.repository.DefaultPersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.data.repository.DefaultPersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindFilePersonLoader(
        defaultFilePersonLoader: DefaultFilePersonLoader
    ): FilePersonLoader

    @Binds
    @Singleton
    fun bindPersonLoaderRepository(
        defaultPersonLoaderRepository: DefaultPersonLoaderRepository
    ): PersonLoaderRepository

    @Binds
    @Singleton
    fun bindPersonRepository(
        defaultPersonRepository: DefaultPersonRepository
    ): PersonRepository
}