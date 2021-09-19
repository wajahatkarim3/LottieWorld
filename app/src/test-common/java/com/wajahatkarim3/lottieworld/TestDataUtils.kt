package com.wajahatkarim3.lottieworld

import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.remote.responses.*

object TestDataUtils {
    fun createBlogsApiResponse(count: Int): BlogsDataResponse {
        return BlogsDataResponse(
            BlogsData(
                currentPage = 1,
                perPage = 10,
                totalPages = 10,
                from = 1,
                to = 10,
                total = 1435,
                results = createBlogItemsList(count)
            )
        )
    }

    fun createBlogItemsList(count: Int): List<BlogModel> {
        return (0 until count).map {
            BlogModel(
                title = "Test Blog Title ${it}",
                imageUrl = "https://d3jl769oy69y7b.cloudfront.net/2021/06/Embed-Blog-OG.png",
                postedAt = "2021-07-08T00:00:00.000Z"
            )
        }
    }

    fun createAnimationsDataResponse(count: Int): AnimationsDataResponse {
        return AnimationsDataResponse(
            AnimationsData(
                currentPage = 1,
                perPage = 10,
                totalPages = 10,
                from = 1,
                to = 10,
                total = 1435,
                results = createAnimationsList(3)
            )
        )
    }

    fun createAnimationsList(count: Int): List<AnimationModel> {
        return (0 until count).map {
            AnimationModel(
                id = it.toLong(),
                name = "Animation ${it}",
                imageUrl = "https://assets1.lottiefiles.com/render/koryu6lo.png",
                lottieUrl = "https://assets10.lottiefiles.com/packages/lf20_aakruwkk.json",
                gifUrl = "https://assets1.lottiefiles.com/render/koryu6lo.gif",
                createdAt = "2021-05-16T13:55:56.000Z",
                createdBy = createAnimator(),
                videoUrl = "https://assets1.lottiefiles.com/render/koryu6lo.mp4",
                bgColor = "#FFFFFF"
            )
        }
    }

    fun createAnimatorsDataResponse(count: Int) = AnimatorsDataResponse(
        AnimatorsData(
            results = createAnimatorsList(count)
        )
    )

    fun createAnimatorsList(count: Int): List<AnimatorModel> {
        return (0 until count).map {
            createAnimator()
        }
    }

    fun createAnimator() = AnimatorModel(
        name = "Test Animator",
        avatarUrl = "https://assets3.lottiefiles.com/avatars/300_120122-362745608.jpg"
    )

}