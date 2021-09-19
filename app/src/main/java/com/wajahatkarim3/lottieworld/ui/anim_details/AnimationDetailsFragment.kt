package com.wajahatkarim3.lottieworld.ui.anim_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseFragment
import com.wajahatkarim3.lottieworld.base.ContentState
import com.wajahatkarim3.lottieworld.base.ErrorState
import com.wajahatkarim3.lottieworld.base.LoadingState
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.databinding.FragmentAnimationDetailsBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show
import com.wajahatkarim3.lottieworld.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent


@AndroidEntryPoint
class AnimationDetailsFragment: BaseFragment() {

    private lateinit var bi: FragmentAnimationDetailsBinding
    private val viewModel: AnimationDetailsViewModel by viewModels()
    private var currentLottieUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = FragmentAnimationDetailsBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationModel = arguments?.getParcelable<AnimationModel>(AnimationModel.PARCEL_KEY)
        if (animationModel != null) {
            setupViews()
            initObservations()

            viewModel.initAnimation(animationModel)

        } else if (arguments?.getString(AnimationModel.LOTTIE_URL_KEY) != null) {
            setupViews()
            initObservations()

            viewModel.initAnimationFromUrl(arguments?.getString(AnimationModel.LOTTIE_URL_KEY) ?: "")
        } else {
            findNavController().popBackStack()
        }
    }

    fun setupViews() {
        // Play/Pause
        bi.imgPlayPause.setOnClickListener {
            if (bi.lottieAnim.isAnimating) {
                // Pause
                bi.lottieAnim.pauseAnimation()
                bi.imgPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                // Play
                bi.lottieAnim.resumeAnimation()
                bi.imgPlayPause.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        }

        // Like
        bi.fabLike.setOnClickListener {
            viewModel.likeAnimation()
        }

        // Share
        bi.fabShare.setOnClickListener {
            if (currentLottieUrl != null) {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, currentLottieUrl)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }
    }

    fun initObservations() {
        viewModel.animationModel.observe(viewLifecycleOwner) { animation ->
            if (animation != null) {
                bi.apply {
                    fabLike.show()
                    txtName.text = animation.name
                    txtCreatorName.text = animation.createdBy.name
                    imgAvatar.load(animation.createdBy.avatarUrl) {
                        crossfade(true)
                        placeholder(R.color.loading_gray)
                        transformations(CircleCropTransformation())
                    }
                }
            } else {
                bi.fabLike.gone()
            }
        }

        viewModel.lottieUrl.observe(viewLifecycleOwner) { lottieUrl ->
            currentLottieUrl = lottieUrl
            loadAnimation(lottieUrl)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                LoadingState -> {
                    bi.groupAnimDetails.gone()
                    bi.layoutShimmerLoading.root.show()
                }

                ContentState -> {
                    bi.groupAnimDetails.show()
                    bi.layoutShimmerLoading.root.gone()
                }

                is ErrorState -> {
                    bi.groupAnimDetails.gone()
                    bi.layoutShimmerLoading.root.show()
                    findNavController().popBackStack()
                    activity?.showToast(uiState.message)
                }
            }
        }
    }

    private fun loadAnimation(lottieUrl: String) {
        bi.lottieAnim.addLottieOnCompositionLoadedListener {
            bi.apply {
                seekBarAnimation.max = 100
                lottieAnim.loop(true)
                lottieAnim.playAnimation()

                imgPlayPause.setImageResource(R.drawable.ic_baseline_pause_24)

                lottieAnim.addAnimatorUpdateListener {
                    seekBarAnimation.progress = (it.animatedFraction * 100).toInt()
                }
                seekBarAnimation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) {
                            val fraction: Float = (progress / 100.0).toFloat()
                            lottieAnim.progress = fraction
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })

            }

            viewModel.showContent()
        }
        bi.lottieAnim.setFailureListener {
            viewModel.showError(it.localizedMessage)
        }
        bi.lottieAnim.setAnimationFromUrl(lottieUrl)
    }
}