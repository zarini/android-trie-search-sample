package com.milkyhead.app.androidtriesearchsample.data.datasource.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.milkyhead.app.androidtriesearchsample.R
import com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity.PersonEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DefaultFilePersonLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : FilePersonLoader {


    override fun load(): List<PersonEntity> {
        return try {
            context.resources.openRawResource(R.raw.persons).use {
                gson.fromJson(
                    it.bufferedReader().readText(),
                    object : TypeToken<List<PersonEntity>>() {}.type
                ) as List<PersonEntity>
            }
        } catch (ex: Exception) {
            emptyList()
        }
    }
}