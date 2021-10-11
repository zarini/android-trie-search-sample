package com.milkyhead.app.androidtriesearchsample.domain.model

import com.milkyhead.app.trie.TrieModelWithKey


data class Person(
    val name: String,
    val family: String,
    val age: Int
) : TrieModelWithKey(name)