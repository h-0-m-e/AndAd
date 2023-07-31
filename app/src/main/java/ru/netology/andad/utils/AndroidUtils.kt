package ru.netology.andad.utils

import android.content.Context

object AndroidUtils {

    fun dp(context:Context, dp: Int): Int =
        kotlin.math.ceil(context.resources.displayMetrics.density * dp).toInt()
}
