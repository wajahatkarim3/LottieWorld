package com.wajahatkarim3.lottieworld.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseRecyclerViewAdapter
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.databinding.ItemNewsLayoutBinding

class NewsAdapter: BaseRecyclerViewAdapter<NewsAdapter.ViewHolder, BlogModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val itemBinding: ItemNewsLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: BlogModel) {
            itemBinding.apply {
                txtTitle.text = item.title
                imgCover.load(item.imageUrl) {
                    crossfade(true)
                    placeholder(R.color.loading_gray)
                }
            }
        }
    }
}
