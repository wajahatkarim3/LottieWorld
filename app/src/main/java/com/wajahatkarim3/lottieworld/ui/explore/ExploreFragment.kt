package com.wajahatkarim3.lottieworld.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajahatkarim3.lottieworld.adapters.AnimationsAdapter
import com.wajahatkarim3.lottieworld.adapters.AnimatorsAdapter
import com.wajahatkarim3.lottieworld.base.*
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.databinding.FragmentExploreBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show
import com.wajahatkarim3.lottieworld.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment: BaseFragment() {

    private lateinit var bi: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModels()
    private var animatorsAdapter: AnimatorsAdapter? = null
    private var featuredAdapter: AnimationsAdapter? = null
    private var popularAdapter: AnimationsAdapter? = null
    private var recentAdapter: AnimationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = FragmentExploreBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        initObservations()
    }

    private fun setupViews() {
        // Animators
        animatorsAdapter = AnimatorsAdapter()
        bi.recyclerAnimators.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = animatorsAdapter
        }

        // Featured
        featuredAdapter = AnimationsAdapter(::onAnimationClick, ::onAnimationLiked)
        bi.recyclerFeaturedAnimations.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = featuredAdapter
        }

        // Recent
        recentAdapter = AnimationsAdapter(::onAnimationClick, ::onAnimationLiked)
        bi.recyclerRecentAnimations.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentAdapter
        }

        // Popular
        popularAdapter = AnimationsAdapter(::onAnimationClick, ::onAnimationLiked)
        bi.recyclerPopularAnimations.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

        // Retry
        bi.layoutRetry.btnRetry.setOnClickListener {
            bi.apply {
                layoutRetry.root.gone()
                lblAnimators.show()
                lblFeaturedAnimations.show()
                lblPopularAnimations.show()
                lblRecentAnimations.show()
            }

            viewModel.refresh()
        }
    }

    private fun initObservations() {
        viewModel.uiAnimatorsState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is LoadingState -> {
                    bi.layoutShimmerAnimators.root.show()
                    bi.recyclerAnimators.gone()
                }

                is ContentState -> {
                    bi.layoutShimmerAnimators.root.gone()
                    bi.recyclerAnimators.show()
                }

                is EmptyState -> {
                    bi.layoutShimmerAnimators.root.gone()
                    bi.recyclerAnimators.gone()
                    bi.lblAnimators.gone()
                }

                is ErrorState -> {
                    bi.layoutShimmerAnimators.root.gone()
                    bi.recyclerAnimators.gone()
                    bi.lblAnimators.gone()
                    showFullError(uiState.message)
                }
            }
        }

        viewModel.uiFeaturedState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is LoadingState -> {
                    bi.layoutShimmerFeaturedAnims.root.show()
                    bi.recyclerFeaturedAnimations.gone()
                }

                is ContentState -> {
                    bi.layoutShimmerFeaturedAnims.root.gone()
                    bi.recyclerFeaturedAnimations.show()
                }

                is EmptyState -> {
                    bi.layoutShimmerFeaturedAnims.root.gone()
                    bi.recyclerFeaturedAnimations.gone()
                    bi.lblFeaturedAnimations.gone()
                }

                is ErrorState -> {
                    bi.layoutShimmerFeaturedAnims.root.gone()
                    bi.recyclerFeaturedAnimations.gone()
                    bi.lblFeaturedAnimations.gone()
                    showFullError(uiState.message)
                }
            }
        }
        viewModel.uiRecentState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is LoadingState -> {
                    bi.layoutShimmerRecentAnims.root.show()
                    bi.recyclerRecentAnimations.gone()
                }

                is ContentState -> {
                    bi.layoutShimmerRecentAnims.root.gone()
                    bi.recyclerRecentAnimations.show()
                }

                is EmptyState -> {
                    bi.layoutShimmerRecentAnims.root.gone()
                    bi.recyclerRecentAnimations.gone()
                    bi.lblRecentAnimations.gone()
                }

                is ErrorState -> {
                    bi.layoutShimmerRecentAnims.root.gone()
                    bi.recyclerRecentAnimations.gone()
                    bi.lblRecentAnimations.gone()
                    showFullError(uiState.message)
                }
            }
        }
        viewModel.uiPopularState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is LoadingState -> {
                    bi.layoutShimmerPopularAnims.root.show()
                    bi.recyclerPopularAnimations.gone()
                }

                is ContentState -> {
                    bi.layoutShimmerPopularAnims.root.gone()
                    bi.recyclerPopularAnimations.show()
                }

                is EmptyState -> {
                    bi.layoutShimmerPopularAnims.root.gone()
                    bi.recyclerPopularAnimations.gone()
                    bi.lblPopularAnimations.gone()
                }

                is ErrorState -> {
                    bi.layoutShimmerPopularAnims.root.gone()
                    bi.recyclerPopularAnimations.gone()
                    bi.lblPopularAnimations.gone()
                    showFullError(uiState.message)
                }
            }
        }

        viewModel.animatorsList.observe(viewLifecycleOwner) { animators ->
            animatorsAdapter?.replaceList(animators)
        }

        viewModel.featuredAnimsList.observe(viewLifecycleOwner) { anims ->
            featuredAdapter?.replaceList(anims)
        }
        viewModel.popularAnimsList.observe(viewLifecycleOwner) { anims ->
            popularAdapter?.replaceList(anims)
        }
        viewModel.recentAnimsList.observe(viewLifecycleOwner) { anims ->
            recentAdapter?.replaceList(anims)
        }
    }

    private fun showFullError(message: String) {
        bi.apply {
            if (recyclerAnimators.isVisible.not() && recyclerFeaturedAnimations.isVisible.not() && recyclerRecentAnimations.isVisible.not() && recyclerPopularAnimations.isVisible.not()) {
                layoutRetry.root.show()
                layoutRetry.txtSubHeading.text = message
                lblAnimators.gone()
                lblFeaturedAnimations.gone()
                lblPopularAnimations.gone()
                lblRecentAnimations.gone()
            } else {
                layoutRetry.root.gone()
                lblAnimators.show()
                lblFeaturedAnimations.show()
                lblPopularAnimations.show()
                lblRecentAnimations.show()
            }
        }
    }

    fun onAnimationClick(animation: AnimationModel) {

    }

    fun onAnimationLiked(animation: AnimationModel) {
        viewModel.addToFavorite(animation)
    }
}