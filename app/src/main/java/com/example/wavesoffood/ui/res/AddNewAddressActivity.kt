package com.example.wavesoffood.ui.res

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Models.AddressModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityAddNewAddressBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import java.util.Locale

class AddNewAddressActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_PERMSSION_REQEST = 1001
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var binding: ActivityAddNewAddressBinding
    private lateinit var currentLatLng: LatLng
    private lateinit var addressModel: AddressModel
    private lateinit var currentLable : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Firebase
        FirebaseApp.initializeApp(this)
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var dbAddRef = db.getReference("address")
        var key = dbAddRef.push().key.toString()
        var ownerID = "admin@admin.com"
        //init

        addressModel = AddressModel()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        currentLable = binding.btnHome
        currentLable.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))

        binding.btnTarget.setOnClickListener {
            getPermission()
        }
        binding.btnWork.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnWork
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))

        }
        binding.btnOther.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnOther
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))
        }
        binding.btnHome.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnHome
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))
        }


        binding.btnSave.setOnClickListener {
           var lat : Double
            var lon : Double
            var myAddressModel = AddressModel()
            var fullAdd = binding.etFullAddress.text.toString().trim()
            var street = binding.etStreet.text.toString().trim()
            var pincode = binding.etPin.text.toString().trim()
            var appartment = binding.etApartmentNo.text.toString().trim()
            var label = currentLable.text.toString().trim()
            if (::currentLatLng.isInitialized){
                lat = currentLatLng.latitude
                lon = currentLatLng.longitude
                myAddressModel.latitude = lat
                myAddressModel.longitude = lon

            }
            if (fullAdd.isEmpty() || street.isEmpty() || pincode.isEmpty() || appartment.isEmpty() || label.isEmpty()){
                Toast.makeText(this,"All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addressModel.fullAdd = fullAdd
            addressModel.street = street
            addressModel.pincode = pincode.toInt()
            addressModel.apartmentNo = appartment
            addressModel.label = label
            addressModel.owner = "admin@admin.com"
            addressModel.id = ownerID

            dbAddRef.child(key).setValue(addressModel).addOnSuccessListener { result->
                Toast.makeText(this@AddNewAddressActivity,"Saved", Toast.LENGTH_SHORT).show()
                onBackPressedDispatcher
            }
        }



    }

    fun getPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMSSION_REQEST
            )
        } else {
            getLocation()
        }
    }

    fun isGpsOn(): Boolean {

        val loadActionManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return loadActionManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getPermission()
        } else {
            if (isGpsOn()) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        var lat = location.latitude
                        var lon = location.longitude
                        addressModel.longitude = lon
                        addressModel.latitude = lat
                        currentLatLng = LatLng(lat, lon)
                        updateMapLocation()
                        getAddressMapping(lat, lon)

                    }

                }

            } else {
                Toast.makeText(this@AddNewAddressActivity, "GPS in not On", Toast.LENGTH_SHORT)
                    .show()
                AlertDialog.Builder(this@AddNewAddressActivity).setTitle("GPS is Off")
                    .setMessage("Please Open GPS").setPositiveButton("Open") { dialog, which ->
                        var intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }.setNegativeButton("Close") { dialog, which ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    private fun updateMapLocation() {
        if (::googleMap.isInitialized && ::currentLatLng.isInitialized) {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Selected Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
        }
    }

    private fun getAddressMapping(lat: Double, lon: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            var address = addresses[0]
            var myFullAdd = address.getAddressLine(0)

            var pincode = address.postalCode


            binding.etFullAddress.setText(myFullAdd)
            binding.etPin.setText(pincode.toString())

        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if (::currentLatLng.isInitialized) {
            updateMapLocation()
        } else {
            var location = LatLng(28.6139, 77.2090)
            p0.addMarker(MarkerOptions().position(location).title("selected location"))
            p0.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 2f))
        }

    }
}