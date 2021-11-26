package id.elfastudio.rijksmuseum.others

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat

fun View.hide() = run { visibility = View.GONE }

fun View.show() = run { visibility = View.VISIBLE }

fun Context.getColorCompat(colorId: Int) = ContextCompat.getColor(this, colorId)