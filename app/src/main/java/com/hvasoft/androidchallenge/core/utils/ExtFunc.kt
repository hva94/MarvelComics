package com.hvasoft.androidchallenge.core.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import java.security.MessageDigest

object ExtFunc {

    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    fun Fragment.showMessage(msg: Any, isError: Boolean = false) {
        val duration = if (isError) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        val message = if (msg is Int) getString(msg) else msg.toString()
        view?.let { Snackbar.make(it, message, duration).show() }
    }

    fun ImageView.loadImage(url: String?) {
        url?.let {
            if (url.startsWith("https")) {
                Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .centerCrop()
                    .into(this)
            } else {
                Glide.with(this)
                    .load(url.replace("http", "https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .centerCrop()
                    .into(this)
            }
        }
    }

    fun Fragment.hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}