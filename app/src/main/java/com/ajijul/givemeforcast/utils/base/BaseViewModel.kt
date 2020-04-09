package com.ajijul.givemeforcast.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    protected var screenState = MutableLiveData<ScreenState>().apply { value = ScreenState.RENDER }


}