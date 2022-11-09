package com.easemob.buddy.tool

import android.content.res.Resources
import android.util.Size
import android.util.TypedValue
import java.math.RoundingMode
import java.text.DecimalFormat

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

fun Int.number2K(): String {
    if (this < 1000) return this.toString()
    val format = DecimalFormat("0.#")
    //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return "${format.format(this / 1000f)}k"
}