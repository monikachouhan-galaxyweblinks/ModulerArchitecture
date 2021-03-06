package com.gwl.fblogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.gwl.MyApplication
import com.gwl.model.User
import com.gwl.navigation.features.HomeNavigation

class FacebookActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    val loginManager by lazy { MyApplication.loginManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)
        getFacebookUserInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Initialize Facebook sdk
     */
    private fun getFacebookUserInfo() {
        callbackManager = CallbackManager.Factory.create()
        FacebookSdk.sdkInitialize(this)
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(
            this, listOf("public_profile", "email")
        )
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                //Called when login success
                val request = GraphRequest.newMeRequest(loginResult?.accessToken)
                { `object`, response ->
                    try {
                        val userIdStr = `object`.getString("id")
                        val userNameStr = `object`.getString("name")
                        val emailIdStr = `object`.getString("email")
                        val profileLinkStr =
                            "http://graph.facebook.com/$userIdStr/picture?type=large"
                        val user = User().apply { name = userNameStr
                            email = emailIdStr
                            profileUrl = profileLinkStr
                        }
                        loginManager.setUser(user)
                        navigateOnHome()
                    } catch (e: Exception) {
                        finish()
                        Log.e("FBError", e.toString())
                    }
                    finish()
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,birthday,link")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                //Called when login cancel
                Log.e("onCancel", "onCancel")
                finish()
            }

            override fun onError(exception: FacebookException) {
                //Called when login error
                Log.e("onError", exception.toString())
            }
        })
    }

    private fun navigateOnHome() {
        HomeNavigation.dynamicStart?.let {
            startActivity(it)
            finish()
        }
    }
}
