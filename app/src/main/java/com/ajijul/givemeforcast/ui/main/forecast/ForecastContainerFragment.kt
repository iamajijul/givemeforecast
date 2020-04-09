package com.ajijul.givemeforcast.ui.main.forecast

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ajijul.givemeforcast.R
import com.ajijul.givemeforcast.models.forecast.ThreeHoursModel
import com.ajijul.givemeforcast.utils.Constants
import com.ajijul.givemeforcast.utils.Constants.LOCATION_PERMISSION_ID
import com.ajijul.givemeforcast.utils.Constants.REQUEST_CHECK_SETTINGS
import com.ajijul.givemeforcast.utils.base.BaseFragment
import com.ajijul.givemeforcast.utils.base.MarshmallowPermissionHelper
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import com.ajijul.givemeforcast.utils.base.ScreenState
import com.ajijul.givemeforcast.viewmodels.ViewModelProviderFactory
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject


class ForecastContainerFragment : BaseFragment() {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    lateinit var forevcastViewModel: ForecastViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forevcastViewModel =
            ViewModelProvider(
                activity!!,
                viewModelProviderFactory
            ).get(ForecastViewModel::class.java)

        handleLocation()
        subscribeToObsever()
    }

    private fun setViewPager(threeHoursModel: Map<String, List<ThreeHoursModel>>?) {

        forecastFragment_viewPager.adapter =
            PagerAdapter(childFragmentManager, threeHoursModel ?: return)
        forecastFragment_tab.setupWithViewPager(forecastFragment_viewPager)
    }

    private fun handleLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context ?: return);
        if (MarshmallowPermissionHelper.getLocationPermission(
                this,
                activity!!, LOCATION_PERMISSION_ID
            )
        ) {
            displayLocationSettingsRequest(context ?: return)
        }
    }

    private fun subscribeToObsever() {
        forevcastViewModel.observeScreenState().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                when (it) {
                    ScreenState.RENDER -> handleProgress(false)
                    ScreenState.LOADING -> handleProgress(true)
                    ScreenState.ERROR -> handleProgress(false)
                }
            }
        })

        forevcastViewModel.observeGroupData().observe(viewLifecycleOwner, Observer {
            if (it != null ) {
                setViewPager(it)
            }
        })

    }

    private fun observeForecastResult(lat: String, lon: String) {

        forevcastViewModel.getForecastResult()
            .removeObservers(viewLifecycleOwner)
        forevcastViewModel.observeForecast(lat, lon, Constants.API_KEY)
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {


                    when (it) {
                        is ResultWrapper.Success -> {
                            // binding.data = it.value
                            messageHandlerImp.showSnackSuccess(
                                mainView,
                                R.string.succesfullyFetch,
                                true
                            )

                        }
                        is ResultWrapper.GenericError -> {

                            messageHandlerImp.showSnackErrorWithAction(mainView, it.error ?: "") {
                                observeForecastResult(lat, lon)

                            }

                        }
                        ResultWrapper.NetworkError -> {

                            messageHandlerImp.showSnackErrorWithAction(
                                mainView,
                                R.string.retry_text
                            ) {

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == LOCATION_PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayLocationSettingsRequest(context!!)
            }
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.i(
                        "LOGGG",
                        "User agreed to make required location settings changes." + data?.getStringExtra(
                            "result"
                        )
                    )
                    getLastLocation()
                }
                Activity.RESULT_CANCELED -> {
                }
            }
        }
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            handleProgress(false)
            val mLastLocation = locationResult.lastLocation
            Log.i("TAG", locationResult.lastLocation.latitude.toString())
            observeForecastResult(
                locationResult.lastLocation.latitude.toString(),
                locationResult.lastLocation.longitude.toString()
            )
            fusedLocationClient?.removeLocationUpdates(this)
        }
    }

    private fun getLastLocation() {

        handleProgress(true)
        fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
            location?.let {
                handleProgress(false)
                Log.e("MainActivity", location.latitude.toString())
                observeForecastResult(location.latitude.toString(), location.longitude.toString())
                return@addOnSuccessListener
            }
            requestNewLocationData()
        }


    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        fusedLocationClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            null
        )
    }

    private fun displayLocationSettingsRequest(context: Context) {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result =
            LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

        result.addOnFailureListener {
            var status = (it as ApiException).getStatusCode();
            when (status) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        "LOGGG",
                        "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                    )

                    try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        val resolvable = it as ResolvableApiException
                        this.startIntentSenderForResult(
                            resolvable.resolution.intentSender,
                            REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null
                        )

                    } catch (e: IntentSender.SendIntentException) {
                        Log.i("LOGGG", "PendingIntent unable to execute request.")
                    }

                }
            }

        }

        result.addOnSuccessListener {
            val status = it.locationSettingsStates.isGpsPresent
            if (status) {
                Log.i("LOGGG", "All location settings are satisfied.")
                getLastLocation()
            }
        }
    }
}