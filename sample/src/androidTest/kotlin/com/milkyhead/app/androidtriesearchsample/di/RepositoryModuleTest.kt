package com.milkyhead.app.androidtriesearchsample.di

import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class RepositoryModuleTest {

    @Provides
    fun providePersonLoaderRepository(): PersonLoaderRepository {
        return FakePersonLoaderRepository()
    }

    @Provides
    fun providePersonRepository(): PersonRepository {
        return FakePersonRepository()
    }

}