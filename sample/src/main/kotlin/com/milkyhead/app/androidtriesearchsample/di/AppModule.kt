package com.milkyhead.app.androidtriesearchsample.di

import com.google.gson.Gson
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.trie.Trie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    fun providePersonTrieStructure(): Trie<Person> = Trie()
}