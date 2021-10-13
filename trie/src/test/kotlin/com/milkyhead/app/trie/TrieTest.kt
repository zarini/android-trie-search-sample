package com.milkyhead.app.trie

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class TrieTest {

    private val trie: DefaultTrie<Person> = DefaultTrie()

    @Before
    fun setup() {
        trie.clear()
    }

    @Test
    fun `insert person list to structure return true`() {
        val list = produceTestPersonList()
        assertThat(trie.insert(list)).isTrue()
    }

    @Test
    fun `insert empty list to structure return false`() {
        val list = emptyList<Person>()
        assertThat(trie.insert(list)).isFalse()
    }

    @Test
    fun `insert duplicated person list to structure return true`() {
        val list = produceTestPersonList()
        trie.insert(list)
        assertThat(trie.insert(list)).isTrue()
    }

    @Test
    fun `insert duplicated person list to structure dose not increase length`() {
        val list = produceTestPersonList()
        val len = list.size
        trie.insert(list)
        trie.insert(list)
        assertThat(trie.length()).isEqualTo(len)
    }

    @Test
    fun `insert duplicated element in list must not increase structure size`() {
        val list = produceTestPersonListWithDuplicated()
        trie.insert(list)
        assertThat(trie.length()).isEqualTo(list.size - 1)
    }

    @Test
    fun `search empty value return all data`() {
        val list = produceTestPersonList()
        val len = list.size
        trie.insert(list)
        assertThat(trie.search("").size).isEqualTo(len)
    }

    @Test
    fun `search value that not exist in structure return empty list`() {
        val list = produceTestPersonList()
        trie.insert(list)
        assertThat(trie.search("ABC").size).isEqualTo(0)
    }

    @Test
    fun `find all person that start with given prefix`() {
        val list = produceTestPersonList()
        trie.insert(list)
        assertThat(trie.search("j").size).isEqualTo(2)
    }

    @Test
    fun `search return sorted list`() {
        val list = produceTestPersonList()
        val sorted = list.sortedBy { it.key }
        trie.insert(list)
        assertThat(trie.search("")).isEqualTo(sorted)
    }

    @Test
    fun `get all return list of all data in structure`() {
        val list = produceTestPersonList()
        trie.insert(list)
        assertThat(trie.all().size).isEqualTo(list.size)
    }

    @Test
    fun `get all data must return all data except duplicated`() {
        val list = produceTestPersonListWithDuplicated()
        trie.insert(list)
        assertThat(trie.all().size).isEqualTo(5)
    }

    @Test
    fun `get all data sorted alphabetically`() {
        val list = produceTestPersonList()
        val sorted = list.sortedBy { it.key }
        trie.insert(list)
        assertThat(trie.all()).isEqualTo(sorted)
    }

    @Test
    fun `clear must remove all data in structure`() {
        val list = produceTestPersonList()
        trie.insert(list)
        trie.clear()
        assertThat(trie.all().size).isEqualTo(0)
    }

    private fun produceTestPersonList(): List<Person> {
        return listOf(
            Person("Amir", "Zarini", 33),
            Person("Jack", "Maco", 25),
            Person("Sarah", "Freakshow", 19),
            Person("xaniar", "Bakhshi", 42),
            Person("Jill", "Jillian", 23),
        )
    }

    private fun produceTestPersonListWithDuplicated(): List<Person> {
        return listOf(
            Person("Amir", "Zarini", 33),
            Person("Amir", "Zarini", 33),
            Person("Jack", "Maco", 25),
            Person("Sarah", "Freakshow", 19),
            Person("xaniar", "Bakhshi", 42),
            Person("Jill", "Jillian", 23),
        )
    }

    private data class Person(
        val name: String,
        val family: String,
        val age: Int
    ) : TrieModelWithKey("$name$family$age")
}