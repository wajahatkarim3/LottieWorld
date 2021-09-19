package com.wajahatkarim3.lottieworld.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.LottieAnimationView
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseRecyclerViewAdapter
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.databinding.ItemAnimationLayoutBinding
import com.wajahatkarim3.lottieworld.databinding.ItemFavoriteAnimLayoutBinding
import com.wajahatkarim3.lottieworld.utils.DoubleClickListener
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show

class AnimationsAdapter(
    val onAnimationClick: (animation: AnimationModel) -> Unit,
    val onAnimationLiked: (animation: AnimationModel) -> Unit,
    val fullWidth: Boolean = false
): BaseRecyclerViewAdapter<AnimationsAdapter.ViewHolder, AnimationModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = if (fullWidth.not()) ItemAnimationLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ) else ItemFavoriteAnimLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder: RecyclerView.ViewHolder {

        lateinit var imgAnimation: ImageView
        lateinit var imgAvatar: ImageView
        lateinit var txtName: TextView
        lateinit var txtCreatorName: TextView
        lateinit var lottieLike: LottieAnimationView
        lateinit var root: ConstraintLayout

        constructor(itemBinding: ViewBinding): super(itemBinding.root) {
            if (itemBinding is ItemAnimationLayoutBinding) {
                imgAnimation = itemBinding.imgAnimation
                imgAvatar = itemBinding.imgAvatar
                txtName = itemBinding.txtName
                txtCreatorName = itemBinding.txtCreatorName
                lottieLike = itemBinding.lottieLike
                root = itemBinding.root
            } else if (itemBinding is ItemFavoriteAnimLayoutBinding) {
                imgAnimation = itemBinding.imgAnimation
                imgAvatar = itemBinding.imgAvatar
                txtName = itemBinding.txtName
                txtCreatorName = itemBinding.txtCreatorName
                lottieLike = itemBinding.lottieLike
                root = itemBinding.root
            }
        }

        fun bind(item: AnimationModel) {
            val url = if (item.gifUrl != null) item.gifUrl else item.imageUrl
            imgAnimation.load(url) {
                crossfade(true)
                placeholder(R.color.loading_gray)
            }

            txtName.text = item.name

            txtCreatorName.text = item.createdBy.name
            imgAvatar.load(item.createdBy.avatarUrl) {
                crossfade(true)
                placeholder(R.color.loading_gray)
                transformations(CircleCropTransformation())
            }

            root.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick() {
                    onAnimationClick.invoke(item)
                }

                override fun onDoubleClick() {
                    lottieLike.addAnimatorUpdateListener {
                        if (it.animatedFraction >= 1.0) {
                            lottieLike.gone()
                            lottieLike.removeAllUpdateListeners()
                        }
                    }
                    lottieLike.show()
                    lottieLike.playAnimation()
                    onAnimationLiked.invoke(item)
                }
            })
        }
    }
}