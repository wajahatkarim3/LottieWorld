package com.wajahatkarim3.lottieworld.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wajahatkarim3.lottieworld.data.model.BlogModel

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
    @Expose val results: List<BlogModel>
)