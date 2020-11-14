package com.example.moviesapp.utils

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

class Connectivity(val context: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    private val networkRequestBuilder: NetworkRequest.Builder = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            //targeting apilevel >= 25 (>= nougat)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                connectivityManager.registerDefaultNetworkCallback(
                    getConnectivityMarshmallowManagerCallback()
                )

            //targeting apilevel >= 23 (>= marshemello)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                marshmallowNetworkAvailableRequest()

            //targeting apilevel >= 21 (>= lollipop)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
                lollipopNetworkAvailableRequest()

            else -> {
                    context.registerReceiver(
                        networkReceiver,
                        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                    )
            }
        }
    }

    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkAvailableRequest() {
        connectivityManager.registerNetworkCallback(
            networkRequestBuilder.build(),
            getConnectivityLollipopManagerCallback()
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun marshmallowNetworkAvailableRequest() {
        connectivityManager.registerNetworkCallback(
            networkRequestBuilder.build(),
            getConnectivityMarshmallowManagerCallback()
        )
    }

    private fun getConnectivityLollipopManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
        } else {
            throw IllegalAccessError("Accessing wrong API version")
        }
    }

    private fun getConnectivityMarshmallowManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    ) {
                        postValue(true)
                    }
                }

                override fun onLost(network: Network) {
                    postValue(false)
                }
            }
            return networkCallback
        } else {
            throw IllegalAccessError("Accessing wrong API version")
        }

    }
}