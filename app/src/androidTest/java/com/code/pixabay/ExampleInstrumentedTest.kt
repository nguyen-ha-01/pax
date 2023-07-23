package com.code.pixabay

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.code.pixabay.data.network.Rest
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.code.pixabay", appContext.packageName)
    }
    @Test
    fun rest()= runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val load = Rest(appContext)
        val time = measureTimeMillis {
            val loader = async {  load.pixabayRest.getImages("daisy","vector",1,20)}.await()
            println("size ${loader.hits.size}")
            Log.d("TEST",  "size ${loader.hits.size}")
        }
        println("total loading time $time")
        Log.d("TEST", time.toString())
    }
    @Test
    fun downloadTest(){

    }
}