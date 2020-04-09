package com.ajijul.givemeforcast.utils.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ajijul.givemeforcast.R
import com.ajijul.givemeforcast.utils.Constants
import com.ajijul.givemeforcast.utils.indefiniteSnackbar
import com.ajijul.givemeforcast.utils.longSnackbar
import com.ajijul.givemeforcast.utils.snackbar
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    val TAG = "Base Activity"


    @Inject
    lateinit var messageHandlerImp : MessageHandlerImp

    private val mainView: View by lazy {
        findViewById<View>(android.R.id.content)
    }


}