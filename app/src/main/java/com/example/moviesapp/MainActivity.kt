package com.example.moviesapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviesapp.utils.Connectivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var connectivity: Connectivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivity = Connectivity(this)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity(connectivity)
    }

    fun checkConnectivity(networkConnection: Connectivity) {
        val onlineSnackbar = Snackbar.make(root, getString(R.string.online), Snackbar.LENGTH_SHORT)
        onlineSnackbar.view.setBackgroundColor(Color.parseColor("#00a53c"))
        val offlineSnackbar =
            Snackbar.make(root, getString(R.string.offline), Snackbar.LENGTH_INDEFINITE)

        networkConnection.observe(this, Observer { isConnected ->
            isConnected?.let {
                Log.i(TAG, "Connectivity is: ${it}")
                if (!it) {
                    offlineSnackbar.show()
                } else {
                    onlineSnackbar.show()
                }
            }
        })

    }
}
