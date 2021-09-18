package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
    @Expose val results: AnimationResponse
)

data class AnimationResponse(
    @Expose val id: Long,
    @Expose val bgColor: String,
    @Expose val lottieUrl: String,
    @Expose val gifUrl: String? = null,
    @Expose val videoUrl: String? = null,
    @Expose val imageUrl: String,
    @Expose val createdAt: String,
    @Expose val name: String,
    @Expose val createdBy: AnimatorResponse
)