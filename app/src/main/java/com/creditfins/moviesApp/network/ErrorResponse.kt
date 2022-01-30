package com.creditfins.moviesApp.network

data class ErrorResponse(
    val message: String? = null,
    val errors: Errors? = null
)

data class Errors(
    val name: MutableList<String>? = null,
    val phone: MutableList<String>? = null,
    val email: MutableList<String>? = null,
    val password: MutableList<String>? = null,
    val message: MutableList<String>? = null,
    val payment_details: MutableList<String>? = null,
    val password_confirmation: MutableList<String>? = null,
    val old_password: MutableList<String>? = null,
    val new_password: MutableList<String>? = null,
    val confirmation_code: MutableList<String>? = null,
    val age: MutableList<String>? = null,
    val code: MutableList<String>? = null,
    val id: MutableList<String>? = null,
    val date_birth: MutableList<String>? = null,
    val token_device: MutableList<String>? = null,
    val social_token: MutableList<String>? = null
)