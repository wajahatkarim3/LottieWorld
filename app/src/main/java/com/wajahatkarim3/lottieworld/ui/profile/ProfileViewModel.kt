package com.wajahatkarim3.lottieworld.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.lottieworld.data.*
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.usecases.LoadLoggedInUserUserCase
import com.wajahatkarim3.lottieworld.data.usecases.LoginUserUserCase
import com.wajahatkarim3.lottieworld.data.usecases.LogoutUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginUserUserCase: LoginUserUserCase,
    private val logoutUserUserCase: LogoutUserUserCase,
    private val loadLoggedInUserUserCase: LoadLoggedInUserUserCase
): ViewModel() {

    private val _uiState = MutableLiveData<ProfileUIState>()
    val uiState: LiveData<ProfileUIState> = _uiState

    private val _loggedInUser = MutableLiveData<AnimatorModel?>()
    val loggedInUser: LiveData<AnimatorModel?> = _loggedInUser

    init {
        loadExistingUser()
    }

    fun loadExistingUser() {
        viewModelScope.launch {
            loadLoggedInUserUserCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _loggedInUser.value = this.data
                    if (this.data == null) {
                        _uiState.value = LogoutState
                    } else {
                        _uiState.value = LoginState
                    }
                }.onLoading {
                    _uiState.value = LoadingState
                }.onError {
                    _uiState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUserUserCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _loggedInUser.value = null
                    _uiState.value = LogoutState
                }.onLoading {
                    _uiState.value = LoadingState
                }.onError {
                    _uiState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            loginUserUserCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _loggedInUser.value = dataResource.data
                    _uiState.value = LoginState
                }.onLoading {
                    _uiState.value = LoadingState
                }.onError {
                    _uiState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }
}