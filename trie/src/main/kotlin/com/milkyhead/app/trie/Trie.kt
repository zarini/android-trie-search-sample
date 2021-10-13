package com.milkyhead.app.trie


interface Trie<T> {

    /**
     * Insert list on data
     *
     * @return true if all data inserted
     */
    fun insert(data: List<T>): Boolean

    /**
     * Search specific query in all data with prefix search
     *
     * @return list of data or empty list
     */
    fun search(query: String): List<T>

    /**
     * Get all data
     *
     * @return list of all data or empty list
     */
    fun all(): List<T>

    /**
     * Clear all data
     */
    fun clear()

    /**
     * Get data size
     *
     * @return data size
     */
    fun length(): Int
}