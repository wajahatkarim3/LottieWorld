package com.wajahatkarim3.lottieworld.data.remote

import com.wajahatkarim3.lottieworld.data.remote.responses.LoadAnimationsResponse
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadAnimatorsResponse
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadBlogsResponse
import retrofit2.http.GET

interface LottieFilesApiService {

    @GET("blogs.json?alt=media&token=c6bf2153-7a69-4a47-9e3a-6f7500d8f523")
    suspend fun loadBlogs(): LoadBlogsResponse

    @GET("featuredAnimators.json?alt=media&token=5b3e8205-b317-45c4-a5ce-36c9dc57911d")
    suspend fun loadFeaturedAnimators(): LoadAnimatorsResponse

    @GET("recentAnimations.json?alt=media&token=f5acfd96-384a-4552-a0b5-399675a03826")
    suspend fun loadRecentAnimations(): LoadAnimationsResponse

    @GET("popularAnimations.json?alt=media&token=a32b4948-d278-4923-880e-8fb57741c190")
    suspend fun loadPopularAnimations(): LoadAnimationsResponse

    @GET("featuredAnimations.json?alt=media&token=f6e406f5-befb-40ab-a9b0-bb0a773b09fd")
    suspend fun loadFeaturedAnimations(): LoadAnimationsResponse
}