package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoadAnimatorsResponse(
    @Expose @SerializedName("data")
    val  animatorsData: AnimatorsDataResponse
)

data class AnimatorsDataResponse(
    @Expose @SerializedName("featuredAnimators")
    val blogs: AnimatorsData
)

data class AnimatorsData(
    @Expose val results: AnimatorResponse
)

data class AnimatorResponse(
    @Expose val name: String,
    @Expose val avatarUrl: String
)