package com.gwl.login

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.model.LoginItem
import com.gwl.model.User

class LoginViewModel : BaseViewModel() {

    // region - Public Properties
    val loginItem by lazy { LoginItem(email = "gwl@example.com", password = "Gwl@1234") }
    var btnLoginEnable = ObservableField(false)
    var loadedVisibility = ObservableField(false)
    var clearError = ObservableField(false)
    val loginError by lazy { MutableLiveData<String>() }
    val emailError by lazy { ObservableField<String>() }
    val passwordError by lazy { ObservableField<String>() }
    var navigateOnNext: MutableLiveData<Boolean> = MutableLiveData()
    var navigateForGoogleLogin: MutableLiveData<Boolean> = MutableLiveData()
    var navigateForFacebookLogin: MutableLiveData<Boolean> = MutableLiveData()
    var onLoginError: MutableLiveData<String> = MutableLiveData()
    val showKeyboard by lazy { MutableLiveData<Boolean>() }
    val loginManager by lazy { MyApplication.loginManager }
    // endregion

    // region - onFocusChange validate fields of input text
    fun onFocusChanged(view: View, loginField: LoginField, hasFocus: Boolean) {
        updateError(hasFocus)
        if (loginField == LoginField.PASSWORD)
            showKeyboard.postValue(true)
    }
    // endregion

    // region - Button Clicks
    fun onLoginClick() {
        updateLoading(true)
        showKeyboard.postValue(false)
        if (loginItem.isValidCredential) {
            loginManager.persistUser(true)
            val user = User().apply {
                name = "testUser"
                email = "gwl@example.com"
            }
            loginManager.setUser(user)
            navigateOnNext.postValue(true)

        } else {
            updateLoading(false)
            loginError.value = "Invalid credential."
            onLoginError.postValue(loginError.value)
        }
    }

    fun onGoogleLoginClick() {
        navigateForGoogleLogin.postValue(true)
    }

    fun onFacebookLoginClick() {
        navigateForFacebookLogin.postValue(true)
    }

    // endregion

    // region - After Text change lister  validate form fields and save value on model
    fun afterTextChange(loginField: LoginField, value: String) {
        loginItem.apply {
            when (loginField) {
                LoginField.USER_NAME -> email = value
                LoginField.PASSWORD -> password = value
            }

            val isValid = loginItem.email.isNotEmpty() && loginItem.password.isNotEmpty()
            btnLoginEnable.set(isValid)
        }
    }
    // endregion

    // region - Private function
    private fun updateLoading(b: Boolean) {
        loadedVisibility.set(b)
    }

    private fun updateError(hasFocus: Boolean) {
        clearError.set(hasFocus)
    }
    // endregion
}

enum class LoginField {
    USER_NAME, PASSWORD
}