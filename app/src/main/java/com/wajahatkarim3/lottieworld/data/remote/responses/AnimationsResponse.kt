package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel

data class LoadAnimationsResponse(
    @Expose @SerializedName("data")
    val  animationsData: AnimationsDataResponse
)

data class AnimationsDataResponse(
    @Expose @SerializedName(value = "recent", alternate = ["popular", "featured"])
    val animations: AnimationsData
)

data class AnimationsData(
    @Expose val currentPage: Int,
    @Expose val perPage: Int,
    @Expose val totalPages: Int,
    @Expose val from: Int,
    @Expose val to: Int,
    @Expose val total: Int,
    @Expose val results: List<AnimationModel>
)