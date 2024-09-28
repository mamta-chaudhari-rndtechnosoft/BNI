package com.rndtechnosoft.bconn.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rndtechnosoft.bconn.Fragment.HomeFragment
import com.rndtechnosoft.bconn.Fragment.MyAskFragment
import com.rndtechnosoft.bconn.Fragment.MyGiveFragment
import com.rndtechnosoft.bconn.Fragment.MyMatchFragment
import com.rndtechnosoft.bconn.Fragment.ProfileFragment
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val token:String? = SaveSharedPreference.getInstance(this@MainActivity).getToken()
        val userId:String? = SaveSharedPreference.getInstance(this@MainActivity).getUserId()

        //Toast.makeText(this@MainActivity,"token: $token  userId:$userId",Toast.LENGTH_SHORT).show()

        Log.d("Api Response","token: $token  userId:$userId")

        loadFragment(MyAskFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.myAsk -> {
                    loadFragment(MyAskFragment())
                    true
                }

                R.id.myGive -> {
                    loadFragment(MyGiveFragment())
                    true
                }

                R.id.myMatch -> {
                    loadFragment(MyMatchFragment())
                    true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        checkNotificationPermission()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainContainer.id, fragment)
        transaction.commit()
    }

    fun setSelectedBottomNavItem(itemId: Int) {
        binding.bottomNav.selectedItemId = itemId
    }

    private fun getLastLocation() {
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Permissions are granted, get the location
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        // Got the location
                        val latitude = location.latitude
                        val longitude = location.longitude
                        //Toast.makeText(this, "Lat: $latitude, Long: $longitude", Toast.LENGTH_SHORT).show()
                    } else {
                        // Location is null, handle it
                        //Toast.makeText(this, "Unable to retrieve location", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted, get the location
                getLastLocation()
            } else {
                // Permission denied, handle it
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            } else {
                // Permission is already granted
                //Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            // For Android versions below 13, no need to check or request
            //Toast.makeText(this, "Notification permission not required below Android 13", Toast.LENGTH_SHORT).show()
        }
    }

}