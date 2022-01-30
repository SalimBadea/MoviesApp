package com.creditfins.moviesApp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.creditfins.moviesApp.custom.CustomProgressView

abstract class BaseFragment : Fragment() {

    abstract fun loadLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(loadLayoutResource(), container, false)
        return v
    }

    fun unauthenticated(progress: CustomProgressView?) {
//        progress?.let {
//            it.showUnauthenticated {
//                startActivity(Intent(context, LoginActivity::class.java))
//            }
//        }
    }
}