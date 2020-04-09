package com.ajijul.givemeforcast.ui.main.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ajijul.givemeforcast.R
import com.ajijul.givemeforcast.databinding.FragmentWeatherBinding
import com.ajijul.givemeforcast.utils.Constants
import com.ajijul.givemeforcast.utils.Helper
import com.ajijul.givemeforcast.utils.base.BaseFragment
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import com.ajijul.givemeforcast.utils.base.ScreenState
import com.ajijul.givemeforcast.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject


class WeatherFragment : BaseFragment() {

    lateinit var weatherViewModel: WeatherViewModel

    val TAG = "Weather FRAGMENT"

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherViewModel =
            ViewModelProvider(
                activity!!,
                viewModelProviderFactory
            ).get(WeatherViewModel::class.java)
        subscribeToObsever()
        initializeListener()
        getWeather("Dubai")

    }

    private fun initializeListener() {

        weatherFragment_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Helper.hideKeyboard(activity)
                getWeather(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        weatherFragment_searchView.setOnClickListener { weatherFragment_searchView.isIconified =
            false }
    }

    private fun subscribeToObsever() {
        weatherViewModel.observeScreenState().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                when (it) {
                    ScreenState.RENDER -> handleProgress(false)
                    ScreenState.LOADING -> handleProgress(true)
                    ScreenState.ERROR -> handleProgress(false)
                }
            }
        })

}


private fun getWeather(p0: String?) {
    weatherViewModel.getWeatherObserver().removeObservers(viewLifecycleOwner)
    weatherViewModel.observeWeather(p0?:"Dubai", Constants.API_KEY)
        .observe(viewLifecycleOwner, Observer {

            if (it != null) {
                when (it) {
                    is ResultWrapper.Success -> {
                        binding.data = it.value
                        messageHandlerImp.showSnackSuccess(
                            mainView,
                            R.string.succesfullyFetch,
                            true
                        )
                    }
                    is ResultWrapper.GenericError -> {

                        messageHandlerImp.showSnackErrorWithAction(mainView, it.error ?: "") {
                            getWeather(p0)

                        }

                    }
                    ResultWrapper.NetworkError -> {

                        messageHandlerImp.showSnackErrorWithAction(mainView, R.string.retry_text) {

                        }
                    }
                }
            }
        })


}

private fun handleProgress(isShow: Boolean) {
    if (isShow) {
        progress_bar.visibility = View.VISIBLE
    } else {
        progress_bar.visibility = View.GONE
    }
}

}