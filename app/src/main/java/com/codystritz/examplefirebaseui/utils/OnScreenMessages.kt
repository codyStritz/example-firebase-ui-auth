package com.codystritz.examplefirebaseui.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object OnScreenMessages {
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun showVerifyEmailDialog(context: Context, positiveAction: () -> (Unit)) {
        AlertDialog.Builder(context)
            .setTitle("Notice")
            .setMessage("Please verify your email before signing in")
            .setPositiveButton("Okay") {_,_ -> positiveAction()}
            .create()
            .show()
    }
}