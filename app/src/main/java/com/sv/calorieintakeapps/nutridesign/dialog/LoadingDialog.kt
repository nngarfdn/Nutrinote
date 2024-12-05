package com.sv.calorieintakeapps.nutridesign.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.sv.calorieintakeapps.R

object LoadingDialog {
    private var dialog: Dialog? = null
    fun show(context: Context) {
        if (dialog != null && dialog?.isShowing == true) {
            return
        }
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(view)
        }
        dialog?.show()
    }
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}