package com.milkyhead.app.androidtriesearchsample.data.repository


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.FakeFilePersonLoader
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.FilePersonLoader
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DefaultPersonLoaderRepositoryTest {

    private lateinit var filePersonLoader: FilePersonLoader
    private lateinit var personLoaderRepository: PersonLoaderRepository


    @Before
    fun setUp() {
        filePersonLoader = FakeFilePersonLoader()
        personLoaderRepository = DefaultPersonLoaderRepository(filePersonLoader)
    }

    @Test
    fun `load person from file must succeed`(): Unit = runBlocking {
        assertThat(personLoaderRepository.load()).isNotEmpty()
    }

    @Test
    fun `load person entity from file and map to person must succeed`(): Unit = runBlocking {
        val personsEntity = filePersonLoader.load()
        val persons = personLoaderRepository.load()

        assertThat(personsEntity.size).isEqualTo(persons.size)

        for (index in personsEntity.indices) {
            assertThat(personsEntity[index].firstName).isEqualTo(persons[index].name)
            assertThat(personsEntity[index].lastName).isEqualTo(persons[index].family)
            assertThat(personsEntity[index].age).isEqualTo(persons[index].age)
        }
    }
}