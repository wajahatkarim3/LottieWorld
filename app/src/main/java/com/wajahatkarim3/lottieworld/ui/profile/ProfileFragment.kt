package com.wajahatkarim3.lottieworld.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseFragment
import com.wajahatkarim3.lottieworld.databinding.FragmentProfileBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show
import com.wajahatkarim3.lottieworld.utils.showToast

class ProfileFragment: BaseFragment() {

    private lateinit var bi: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bi = FragmentProfileBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        initObservations()
    }

    fun setupViews() {
        // Logout
        bi.txtSignout.setOnClickListener {
            viewModel.logout()
        }

        // Login
        bi.layoutLogin.btnLogin.setOnClickListener {
            viewModel.login()
        }
    }

    fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                LoadingState -> {
                    bi.groupLoggedIn.gone()
                    bi.layoutShimmerLoading.root.show()
                    bi.layoutLogin.root.gone()
                }

                LoginState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.groupLoggedIn.show()
                    bi.layoutLogin.root.gone()
                }

                LogoutState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.groupLoggedIn.gone()
                    bi.layoutLogin.root.show()
                }

                is ErrorState -> {
                    bi.layoutShimmerLoading.root.gone()
                    bi.groupLoggedIn.gone()
                    bi.layoutLogin.root.show()
                    activity?.showToast(uiState.message)
                }
            }
        }

        viewModel.loggedInUser.observe(viewLifecycleOwner) { user ->
            bi.apply {
                user?.let {
                    imgAvatar.load(user.avatarUrl) {
                        crossfade(true)
                        placeholder(R.color.loading_gray)
                        transformations(CircleCropTransformation())
                    }
                    txtName.text = user.name
                    txtUserName.text = user.username
                }
            }
        }
    }
}