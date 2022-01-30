package com.creditfins.moviesApp.custom

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Base64
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.helper.Logging
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.social_button_layout.view.*
import java.security.MessageDigest

/**
 * Created by µðšţãƒâ ™ on 24/09/2020.
 *  ->
 */
class CustomSocialView : ConstraintLayout {
    private lateinit var mCallbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mSocialCallback: OnSocialClickListener
    private lateinit var mProgressView: CustomProgressView
    private var mEmail: String? = null
    private var mSocialPhoto: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val v = View.inflate(context, R.layout.social_button_layout, this)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomSocialView)

        try {
            val facebookTitle = attr.getString(R.styleable.CustomSocialView_facebook_title)
            val googleTitle = attr.getString(R.styleable.CustomSocialView_google_title)
            val facebookColor = attr.getColor(R.styleable.CustomSocialView_facebook_color, 0)
            val googleColor = attr.getColor(R.styleable.CustomSocialView_google_color, 0)

            mCallbackManager = CallbackManager.Factory.create()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

            v.btnFacebook.setOnClickListener {
//                LoginManager.getInstance().logOut()
//                loginWithFacebook()
            }
            v.btnGoogle.setOnClickListener {
//                mGoogleSignInClient.signOut().addOnCompleteListener(context as Activity) {}
//                loginWithGoogle()
            }
        } finally {
            attr.recycle()
        }
    }

    fun addSocialClickListener(
        socialClickListener: OnSocialClickListener,
        customProgressView: CustomProgressView
    ) {
        mSocialCallback = socialClickListener
        mProgressView = customProgressView
    }

    private fun loginWithFacebook() {
        mProgressView.showProgressView()
        LoginManager.getInstance().logInWithReadPermissions(
            context as Activity, listOf("public_profile", "email")
        )
        LoginManager.getInstance()
            .registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    val request = GraphRequest.newMeRequest(
                        result!!.accessToken
                    ) { obj, response ->
                        Logging.log("Facebook Success : $response")
                        if (obj.has("id")) {
                            mSocialPhoto =
                                "http://graph.facebook.com/" + obj.getString("id") + "/picture?type=large"
                            Logging.log("Facebook id : ${obj.getString("id")} - Image : $mSocialPhoto")
                        }

                        if (obj.has("email")) {
                            mEmail = obj.getString("email")
                            Logging.log("Facebook email : ${obj.getString("email")}")
                        }

                        if (obj.has("name")) {
                            Logging.log("Facebook name : ${obj.getString("name")}")
                        }

                        response.request.accessToken?.let {
                            Logging.log("Facebook accessToken : ${it.token}")
                        }

                        mSocialCallback.onSocialDone(
                            SocialModel(
                                response.request.accessToken?.token ?: "",
                                obj.getString("id"),
                                "facebook",
                                obj.getString("name"),
                                mEmail,
                                mSocialPhoto
                            )
                        )
                        mProgressView.hideProgressView()
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email ")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Logging.log("Facebook Cancel")
                    mProgressView.hideProgressView()
                }

                override fun onError(error: FacebookException?) {
                    Logging.log("Facebook Error : ${error!!.message}")
//                    Logging.toast(context, "${error.message}")
                    mProgressView.hideProgressView()
                }

            })
    }

    private fun loginWithGoogle() {
        mProgressView.showProgressView()
        val signInIntent = mGoogleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, 3)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                mSocialPhoto = account.photoUrl.toString()
                mEmail = account.email
                mSocialCallback.onSocialDone(
                    SocialModel(
                        account.idToken ?: "",
                        account.id ?: "",
                        "google",
                        account.displayName,
                        mEmail,
                        mSocialPhoto
                    )
                )
                Logging.log(
                    "Google SignLoginId " + account.idToken + " - \n" +  // return my account id for google
                            account.id + " - \n" +
                            account.displayName + " - \n" +   // my name first and second
                            account.email + " - \n" +   // return my email
                            account.photoUrl + " - \n" +
                            account.serverAuthCode + " - \n" + // null
                            account.account?.name + " - \n" + // expensesnew@gmail.com
                            account.account?.type + " - \n" + // com.google
                            account.zab() + " - \n" +
                            account.zac()
                )
                mProgressView.hideProgressView()
            }
        } catch (e: ApiException) {
            mProgressView.hideProgressView()
            Logging.log("Google signInResult:failed code= ${e.statusCode}")
//            Logging.toast(context, "Google signInResult:failed code= ${e.statusCode}")
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun SHA28(application: Application) {
        var info: PackageInfo? = null

        try {
            info = application.packageManager.getPackageInfo(
                application.packageName,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) PackageManager.GET_SIGNING_CERTIFICATES
                else PackageManager.GET_SIGNATURES
            )
        } catch (ex: PackageManager.NameNotFoundException) {
            ex.printStackTrace()
        }

        val signatures =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                info?.signingInfo?.signingCertificateHistory
            else info?.signatures

        signatures?.forEach { signature ->
            var md: MessageDigest? = null
            try {
                md = MessageDigest.getInstance("SHA")
            } catch (ex: PackageManager.NameNotFoundException) {
                ex.printStackTrace()
            }
            if (md != null) {
                md.update(signature.toByteArray())
                Logging.log("MY KEY HASH: ${Base64.encodeToString(md.digest(), Base64.DEFAULT)}")
            }
        }
    }
}

@Parcelize
data class SocialModel(
    val socialToken: String,
    val socialId: String,
    val socialType: String,
    val name: String?,
    val email: String?,
    val imageUrl: String?
) : Parcelable

interface OnSocialClickListener {
    fun onSocialDone(data: SocialModel)
}