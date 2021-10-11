package com.milkyhead.app.androidtriesearchsample.data.datasource.local

import com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity.PersonEntity


class FakeFilePersonLoader : FilePersonLoader {


    override fun load(): List<PersonEntity> = arrayListOf(
        PersonEntity("Tito", "Kling", 26),
        PersonEntity("Karina", "Goldner", 21),
        PersonEntity("Freda", "Kuhn", 29),
        PersonEntity("Denis", "Larkin", 39),
        PersonEntity("Kris", "Kub", 11)
    )
}