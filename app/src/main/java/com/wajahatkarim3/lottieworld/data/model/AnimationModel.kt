package com.wajahatkarim3.lottieworld.data.model

import com.google.gson.annotations.Expose

data class AnimationModel(
    @Expose val id: Long,
    @Expose val bgColor: String,
    @Expose val lottieUrl: String,
    @Expose val gifUrl: String? = null,
    @Expose val videoUrl: String? = null,
    @Expose val imageUrl: String,
    @Expose val createdAt: String,
    @Expose val name: String,
    @Expose val createdBy: AnimatorModel
)

sealed class AnimationType
object ANIMATION_TYPE_NONE: AnimationType()
object ANIMATION_TYPE_POPULAR: AnimationType()
object ANIMATION_TYPE_RECENT: AnimationType()
object ANIMATION_TYPE_FEATURED: AnimationType()