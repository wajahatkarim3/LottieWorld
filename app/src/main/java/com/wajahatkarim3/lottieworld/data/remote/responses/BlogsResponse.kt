package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoadBlogsResponse(
    @Expose @SerializedName("data")
    val  blogsData: BlogsDataResponse
)

data class BlogsDataResponse(
    @Expose @SerializedName("blogs")
    val blogs: BlogsData
)

data class BlogsData(
    @Expose val currentPage: Int,
    @Expose val perPage: Int,
    @Expose val totalPages: Int,
    @Expose val from: Int,
    @Expose val to: Int,
    @Expose val total: Int,
    @Expose val results: BlogResponse
)

data class BlogResponse(
    @Expose val title: String,
    @Expose val postedAt: String,
    @Expose val imageUrl: String
)