package com.wajahatkarim3.lottieworld.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseRecyclerViewAdapter
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.databinding.ItemAnimatorLayoutBinding

class AnimatorsAdapter: BaseRecyclerViewAdapter<AnimatorsAdapter.ViewHolder, AnimatorModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnimatorLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val itemBinding: ItemAnimatorLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: AnimatorModel) {
            itemBinding.apply {
                imgAvatar.load(item.avatarUrl) {
                    crossfade(true)
                    placeholder(R.color.loading_gray)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}