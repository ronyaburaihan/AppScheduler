package com.englesoft.appscheduler.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Drawable.toByteArray(): ByteArray {
    val bitmap = Bitmap.createBitmap(
        this.intrinsicWidth,
        this.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)

    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun ByteArray.toDrawable(): Drawable {
    val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
    return BitmapDrawable(Resources.getSystem(), bitmap)
}

fun Long.convertToDateTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    return format.format(date)
}