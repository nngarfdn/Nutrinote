//package com.sv.calorieintakeapps.core.common.util
//
//import android.os.Build
//import android.app.Activity
//import android.net.Uri
//import android.view.View
//import android.widget.ImageView
//import android.widget.Toast
//import com.squareup.picasso.Picasso
//
//val Any.TAG: String
//    get() {
//        return if (!javaClass.isAnonymousClass) {
//            val name = javaClass.simpleName
//            /** First 23 chars */
//            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
//                name.substring(0, 23) else name
//        } else {
//            val name = javaClass.name
//            /** Last 23 chars */
//            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
//                name.substring(name.length - 23, name.length) else name
//        }
//    }
//
//fun Activity.showToast(message: CharSequence?) {
//    Toast.makeText(
//        applicationContext,
//        message,
//        Toast.LENGTH_LONG
//    ).show()
//}
//
//fun ImageView.loadImage(source: String?) {
//    Picasso.get()
//        .load(source)
//        .placeholder(android.R.color.darker_gray)
//        .error(android.R.color.darker_gray)
//        .into(this)
//}
//
//fun ImageView.loadImage(source: Uri?) {
//    Picasso.get()
//        .load(source)
//        .placeholder(android.R.color.darker_gray)
//        .error(android.R.color.darker_gray)
//        .into(this)
//}
//
//fun View.gone(){
//    visibility = View.GONE
//}
//
//fun View.visible(){
//    visibility = View.VISIBLE
//}
//
//fun View.invisible(){
//    visibility = View.INVISIBLE
//}