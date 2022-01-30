package com.creditfins.moviesApp.notifications

import android.os.Bundle
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.helper.Logging
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import org.json.JSONException
import org.json.JSONObject

abstract class NotificationBackground : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getNotificationBody()
    }

    private fun getNotificationBody() {
        val user = SharedPreferencesManager.getUser()
        intent.extras?.let {
            try {
                val body = JSONObject(it["body"].toString())

                Logging.log(
                    """id => ${body.optString("id")}
                    is_rate => ${body.optString("is_rate")}
                    title_ar => ${body.optString("title_ar")}
                    title_en => ${body.optString("title_en")}
                    message_ar => ${body.optString("message_ar")}
                    message_en => ${body.optString("message_en")}
                """.trimMargin()
                )

                val notificationBody =
                    NotificationBody(
                        body.getString("id"),
                        body.getString("is_rate"),
                        body.getString("title"),
                        body.getString("body"),
                        body.getString("image"),
                        body.getString("title_ar"),
                        body.getString("title_en"),
                        body.getString("message_ar"),
                        body.getString("message_en")
                    )

                val title =
                    if (SharedPreferencesManager.getLanguage() == "en") notificationBody.title_en else notificationBody.title_ar
                val message =
                    if (SharedPreferencesManager.getLanguage() == "en") notificationBody.message_en else notificationBody.message_ar

//                if (user.loginIn)
//                    startActivity(
//                        Intent(
//                            this@NotificationBackground,
//                            NotificationActivity::class.java
//                        )
//                    )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}