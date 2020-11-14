package com.example.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.moviesapp.utils.Connectivity

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val connecticity = Connectivity(this)
        connecticity.observe(this, Observer { isConnected ->
            isConnected?.let {
                Log.i(TAG,"Connectivity is: ${it}")
            }
        })
    }
}
