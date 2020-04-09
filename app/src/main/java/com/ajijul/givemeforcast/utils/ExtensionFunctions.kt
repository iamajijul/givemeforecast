package com.ajijul.givemeforcast.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.indefiniteSnackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE)
fun View.indefiniteSnackbar(msg: String) = Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE)
fun View.longSnackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
fun View.snackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
fun Double.toCelsius():String = Math.round(this - 273.15).toString() + 0x00B0.toChar()
