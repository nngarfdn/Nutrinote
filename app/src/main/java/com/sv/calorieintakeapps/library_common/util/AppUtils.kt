package com.sv.calorieintakeapps.library_common.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.sv.calorieintakeapps.R

val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            /** First 23 chars */
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                name.substring(0, 23) else name
        } else {
            val name = javaClass.name
            /** Last 23 chars */
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                name.substring(name.length - 23, name.length) else name
        }
    }

fun Activity.showToast(message: CharSequence?) {
    Toast.makeText(
        applicationContext,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun ImageView.load(
    source: Any?,
    transformation: Transformation = RoundedCornersTransformation(20f),
) {
    load(source) {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
        transformations(transformation)
    }
}

fun View.hideKeyboard() {
    clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}