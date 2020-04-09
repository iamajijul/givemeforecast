package com.ajijul.givemeforcast.utils.base

import android.view.View
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {


    @Inject
    lateinit var messageHandlerImp: MessageHandlerImp

    protected val mainView: View by lazy {
        view!!

    }

}