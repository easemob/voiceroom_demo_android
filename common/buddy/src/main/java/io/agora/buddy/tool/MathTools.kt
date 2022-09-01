package io.agora.buddy.tool

import android.content.res.Resources
import android.util.Size
import android.util.TypedValue

val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

val Number.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun getDisplaySize(): Size {
    val metrics = Resources.getSystem().displayMetrics
    return Size(metrics.widthPixels, metrics.heightPixels)
}