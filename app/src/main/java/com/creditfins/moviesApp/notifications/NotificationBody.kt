package com.creditfins.moviesApp.notifications


data class NotificationBody(
    val user_id: String? = null,
    val is_rate: String? = null,
    val title: String? = null,
    val body: String? = null,
    val image: String? = null,
    val title_ar: String? = null,
    val title_en: String? = null,
    val message_ar: String? = null,
    val message_en: String? = null
)