package com.milkyhead.app.androidtriesearchsample.data.datasource.local

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

class DefaultFilePersonLoaderTest {

    private lateinit var filePersonLoader: FilePersonLoader
    private lateinit var context: Context
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        gson = Gson()
        filePersonLoader = DefaultFilePersonLoader(context, gson)
    }

    @Test
    fun load_persons_from_file_succeed() {
        assertThat(filePersonLoader.load()).isNotEmpty()
    }
}