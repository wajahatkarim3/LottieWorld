package com.wajahatkarim3.lottieworld.data.remote

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadAnimationsResponse
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadBlogsResponse
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class LottieFilesApiServiceTest: ApiBaseTest<LottieFilesApiService>() {

    private lateinit var apiService: LottieFilesApiService

    @Before
    fun setUp() {
        apiService = createService(LottieFilesApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test loadBlogs() returns list of Blogs`() = runBlocking {
        // Given
        enqueueResponse("/blogs_response.json")

        // Invoke
        val response = apiService.loadBlogs()
        val responseBody = requireNotNull((response as LoadBlogsResponse))
        mockWebServer.takeRequest()

        val blogsList = responseBody.blogsData.blogs.results

        // Then
        MatcherAssert.assertThat(blogsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(blogsList[0].title, `is`("The Key to An Immersive UX: Animation"))
        MatcherAssert.assertThat(blogsList[0].imageUrl, `is`("https://d3jl769oy69y7b.cloudfront.net/2021/07/Blog-Visual---The-Key-to-An-Immersive-UX_-Animation.png"))
    }

    @Throws(IOException::class)
    @Test
    fun `test loadFeaturedAnimations() returns list of Animations`() = runBlocking {
        // Given
        enqueueResponse("/featured_animations_response.json")

        // Invoke
        val response = apiService.loadFeaturedAnimations()
        val responseBody = requireNotNull((response as LoadAnimationsResponse))
        mockWebServer.takeRequest()

        val animsList = responseBody.animationsData.animations.results

        // Then
        MatcherAssert.assertThat(animsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsList[0].name, `is`("Pineapple Yoga with music"))
        MatcherAssert.assertThat(animsList[0].id, `is`(62218))
    }
}