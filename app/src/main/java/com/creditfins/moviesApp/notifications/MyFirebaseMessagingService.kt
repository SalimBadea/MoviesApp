package com.creditfins.moviesApp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.common.presentation.activities.MainActivity
import com.creditfins.moviesApp.helper.Logging
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logging.log("Firebase Token : $token")
        SharedPreferencesManager.saveFireBaseToken(token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logging.log("From: ${remoteMessage.from}")
        val messageNotification = remoteMessage.notification?.body

        remoteMessage.data.let {
            Logging.log("Message data payload: " + remoteMessage.data)

            val jsonObject = JSONObject(it as Map<*, *>)
            try {
                val body = JSONObject(jsonObject.getString("body"))
                Logging.log(
                    """id => ${body.optString("id")}
                    is_rate => ${body.optString("is_rate")}
                    title => ${body.optString("title")}
                    body => ${body.optString("body")}
                    title_ar => ${body.optString("title_ar")}
                    title_en => ${body.optString("title_en")}
                    message_ar => ${body.optString("message_ar")}
                    message_en => ${body.optString("message_en")}
                """.trimMargin()
                )

                addNotificationBody(
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
                )
            } catch (e: JSONException) {
                addNotificationBody(
                    NotificationBody(
                        remoteMessage.data["user_id"],
                        remoteMessage.data["is_rate"],
                        remoteMessage.data["title"],
                        remoteMessage.data["body"],
                        remoteMessage.data["image"],
                        remoteMessage.data["title_ar"],
                        remoteMessage.data["title_en"],
                        remoteMessage.data["message_ar"],
                        remoteMessage.data["message_en"]
                    )
                )
                e.printStackTrace()
            }
        }

        remoteMessage.notification?.let {
            Logging.log("Message Notification Body: ${it.body}")
        }
    }

    private fun addNotificationBody(notificationBody: NotificationBody) {
        val title =
            if (SharedPreferencesManager.getLanguage() == "en") notificationBody.title_en else notificationBody.title_ar
        val message =
            if (SharedPreferencesManager.getLanguage() == "en") notificationBody.message_en else notificationBody.message_ar

        sendNotification(notificationBody, title, message)
    }

    private fun sendNotification(
        notificationBody: NotificationBody, title: String?, message: String?
    ) {
        val intent = Intent(this, MainActivity::class.java)


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivities(
            this,
            0 /* Request code */,
            arrayOf(
                Intent(this, MainActivity::class.java)
                    .putExtra("fragmentPosition", 0),
                intent
            ),
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setContentTitle(title ?: notificationBody.title)
            .setContentText(message ?: notificationBody.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}