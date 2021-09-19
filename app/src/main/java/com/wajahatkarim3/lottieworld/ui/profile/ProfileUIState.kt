package com.wajahatkarim3.lottieworld.ui.profile

import com.wajahatkarim3.lottieworld.data.model.AnimatorModel

sealed class ProfileUIState

object LoginState: ProfileUIState()
object LogoutState: ProfileUIState()
object LoadingState: ProfileUIState()
class ErrorState(val message: String): ProfileUIState()