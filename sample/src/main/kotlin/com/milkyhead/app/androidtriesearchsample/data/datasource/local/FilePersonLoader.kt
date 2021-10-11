package com.milkyhead.app.androidtriesearchsample.data.datasource.local

import com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity.PersonEntity


interface FilePersonLoader {

    /**
     * load persons data from file
     */
    fun load(): List<PersonEntity>
}