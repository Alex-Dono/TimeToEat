package com.timetoeat.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class Utils {
    fun showCustomToast(context: android.content.Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        val view = toast.view
        view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        val text = view?.findViewById(android.R.id.message) as TextView
        text.setTextColor(Color.WHITE)
        text.textSize = 20f
        text.gravity = Gravity.CENTER
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

