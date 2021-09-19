package com.wajahatkarim3.lottieworld.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.adapters.AnimationsAdapter
import com.wajahatkarim3.lottieworld.base.*
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.databinding.FragmentFavoritesBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show

class FavoritesFragment: BaseFragment() {

    private lateinit var bi: FragmentFavoritesBinding
    private var recyclerAdapter: AnimationsAdapter? = null
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = FragmentFavoritesBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        initObservations()
    }

    fun setupViews() {
        recyclerAdapter = AnimationsAdapter(::onAnimationClick, {}, fullWidth = true)
        bi.recyclerAnims.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = recyclerAdapter
        }

        // Retry
        bi.layoutRetry.btnRetry.setOnClickListener {
            viewModel.loadAllFavoriteAnims()
        }
    }

    fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                LoadingState -> {
                    bi.layoutShimmerLoading.root.show()
                    bi.layoutRetry.root.gone()
                    bi.recyclerAnims.gone()
                }

                ContentState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.gone()
                    bi.recyclerAnims.show()
                }

                EmptyState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.show()
                    bi.layoutRetry.lottieRetry.setAnimation(R.raw.empty_animation)
                    bi.layoutRetry.txtHeading.text = getString(R.string.double_tap_anim_str)
                    bi.layoutRetry.txtSubHeading.gone()
                    bi.layoutRetry.lottieRetry.playAnimation()
                    bi.recyclerAnims.gone()
                }

                is ErrorState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.layoutRetry.root.show()
                    bi.layoutRetry.lottieRetry.setAnimation(R.raw.retry_animation)
                    bi.layoutRetry.txtSubHeading.text = uiState.message
                    bi.layoutRetry.txtSubHeading.show()
                    bi.layoutRetry.lottieRetry.playAnimation()
                    bi.recyclerAnims.gone()
                }
            }
        }

        viewModel.animsList.observe(viewLifecycleOwner) { animsList ->
            recyclerAdapter?.replaceList(animsList)
        }
    }

    fun onAnimationClick(animation: AnimationModel) {

    }
}