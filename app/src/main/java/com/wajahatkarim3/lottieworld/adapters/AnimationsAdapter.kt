package com.wajahatkarim3.lottieworld.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseRecyclerViewAdapter
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.databinding.ItemAnimationLayoutBinding
import com.wajahatkarim3.lottieworld.utils.DoubleClickListener
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show

class AnimationsAdapter(
    val onAnimationClick: (animation: AnimationModel) -> Unit
): BaseRecyclerViewAdapter<AnimationsAdapter.ViewHolder, AnimationModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnimationLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val itemBinding: ItemAnimationLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: AnimationModel) {
            itemBinding.apply {
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
                    }
                })
            }
        }
    }
}