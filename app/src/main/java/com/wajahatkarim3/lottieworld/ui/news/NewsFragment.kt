package com.wajahatkarim3.lottieworld.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.adapters.NewsAdapter
import com.wajahatkarim3.lottieworld.base.BaseFragment
import com.wajahatkarim3.lottieworld.databinding.FragmentNewsBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: BaseFragment() {

    private lateinit var bi: FragmentNewsBinding
    private var recyclerAdapter: NewsAdapter? = null
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = FragmentNewsBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        initObservations()

        viewModel.loadNews()
    }

    private fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                Loading -> {
                    bi.layoutShimmerLoading.root.show()
                    bi.layoutRetry.root.gone()
                    bi.recyclerNews.gone()
                }

                ContentState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.gone()
                    bi.recyclerNews.show()
                }

                EmptyState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.show()
                    bi.layoutRetry.lottieRetry.setAnimation(R.raw.empty_animation)
                    bi.layoutRetry.txtHeading.text = getString(R.string.no_news_str)
                    bi.layoutRetry.txtSubHeading.gone()
                    bi.layoutRetry.lottieRetry.playAnimation()
                    bi.recyclerNews.gone()
                }

                is Error -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.show()
                    bi.layoutRetry.lottieRetry.setAnimation(R.raw.retry_animation)
                    bi.layoutRetry.txtSubHeading.text = uiState.message
                    bi.layoutRetry.txtSubHeading.show()
                    bi.layoutRetry.lottieRetry.playAnimation()
                    bi.recyclerNews.gone()
                }
            }
        }

        viewModel.blogsList.observe(viewLifecycleOwner) { blogsList ->
            recyclerAdapter?.replaceList(blogsList)
        }
    }

    private fun setupViews() {
        // Recycler
        recyclerAdapter = NewsAdapter()
        bi.recyclerNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = recyclerAdapter
        }

        // Retry
        bi.layoutRetry.btnRetry.setOnClickListener {
            viewModel.loadNews()
        }

    }
}