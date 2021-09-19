package com.wajahatkarim3.lottieworld.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wajahatkarim3.lottieworld.TestDataUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AnimationsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var animationsDao: AnimationsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        animationsDao = database.animationsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun test_insertAnimationsInDatabase() {
        runBlocking {
            // Given
            val givenAnims = TestDataUtils.createAnimationsList(1)

            // Invoke
            animationsDao.insert(givenAnims)

            // Then
            MatcherAssert.assertThat(database.animationsDao().queryAll(), CoreMatchers.equalTo(givenAnims))
        }
    }
}