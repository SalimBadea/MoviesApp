package com.creditfins.moviesApp.network

interface Interactor<in Params, out Type> {

    fun execute(params: Params): Type

    object None
}