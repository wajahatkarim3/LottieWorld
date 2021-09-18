package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel

data class LoadAnimatorsResponse(
    @Expose @SerializedName("data")
    val  animatorsData: AnimatorsDataResponse
)

data class AnimatorsDataResponse(
    @Expose @SerializedName("featuredAnimators")
    val animators: AnimatorsData
)

data class AnimatorsData(
    @Expose val results: List<AnimatorModel>
)