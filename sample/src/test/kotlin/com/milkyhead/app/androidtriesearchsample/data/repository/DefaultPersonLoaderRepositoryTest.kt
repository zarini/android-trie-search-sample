package com.milkyhead.app.androidtriesearchsample.data.repository


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.FilePersonLoader
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity.PersonEntity
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`


class DefaultPersonLoaderRepositoryTest {

    private lateinit var filePersonLoader: FilePersonLoader
    private lateinit var personLoaderRepository: PersonLoaderRepository


    @Before
    fun setUp() {
        filePersonLoader = Mockito.mock(FilePersonLoader::class.java)
        `when`(filePersonLoader.load()).then {
            arrayListOf(
                PersonEntity("Tito", "Kling", 26),
                PersonEntity("Karina", "Goldner", 21),
                PersonEntity("Freda", "Kuhn", 29),
                PersonEntity("Denis", "Larkin", 39),
                PersonEntity("Kris", "Kub", 11)
            )
        }
        personLoaderRepository = DefaultPersonLoaderRepository(filePersonLoader)
    }

    @Test
    fun `load person from file must not empty`(): Unit = runBlocking {
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