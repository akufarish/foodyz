package com.example.stylish.ui.osm

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.Geocoder
import android.location.GpsStatus
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylish.R
import com.example.stylish.adapter.AuthAdapter
import com.example.stylish.adapter.CurrentOrderAdapter
import com.example.stylish.databinding.ActivityOsmBinding
import com.example.stylish.databinding.BottomSheetBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.Locale

@AndroidEntryPoint
class OsmActivity : AppCompatActivity(), MapListener, GpsStatus.Listener {

    private var _binding: ActivityOsmBinding? = null
    private val binding get() = _binding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var isLocationDialogShown = false
    private var isShow = false
    private lateinit var kota: String

    private lateinit var map: MapView
    private var controller: IMapController? = null;
    lateinit var myLocationOverlay: MyLocationNewOverlay
    private val LOCATION_PERMISSION_REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityOsmBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        val map = binding?.osmMapView!!

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val prefs = getSharedPreferences("osmdroid", MODE_PRIVATE)
        Configuration.getInstance().load(applicationContext, prefs)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        map.controller.setZoom(15.0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(!isShow) getLocationAndProceed()

        showBottomSheet()
    }

    private fun showBottomSheet() {
        val bottomSheet = findViewById<View>(R.id.bottomSheetContainer)
        val sheetBinding = BottomSheetBinding.bind(bottomSheet)
        val behavior = BottomSheetBehavior.from(bottomSheet)

        val authAdapter = CurrentOrderAdapter(supportFragmentManager, lifecycle)
        sheetBinding.authViewPager.adapter = authAdapter

        TabLayoutMediator(
            sheetBinding.authTabLayout,
            sheetBinding.authViewPager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.merchant_name)
                1 -> getString(R.string.driver_name)
                else -> ""
            }
        }.attach()

        behavior.peekHeight = 800
        behavior.isHideable = false
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
    

    private fun setUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude

                // ✅ Log lokasi
                Log.d("UserLocation", "Latitude: $latitude, Longitude: $longitude")

                val userLocation = GeoPoint(latitude, longitude)

                val map = binding?.osmMapView!!
                map.controller.setZoom(18.0)
                map.controller.setCenter(userLocation)

                val marker = Marker(map)
                marker.position = userLocation
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "Lokasi Saya"
                map.overlays.add(marker)

                map.invalidate()
            } ?: run {
                Log.w("UserLocation", "Lokasi tidak tersedia (null)")
            }
        }.addOnFailureListener { e ->
            Log.e("UserLocation", "Gagal mendapatkan lokasi: ${e.message}", e)
        }
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    override fun onGpsStatusChanged(p0: Int) {
        TODO("Not yet implemented")
    }

    private fun getLocationAndProceed() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationUpdates()
        } else {
            requestLocationPermission()
        }

    }

    private fun requestLocationUpdates() {
            val locationRequest = LocationRequest.Builder(
                LocationRequest.PRIORITY_HIGH_ACCURACY, 10000
            ).build()

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                startLocationUpdates(locationRequest)
                isShow = true
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        isLocationDialogShown = true
                        exception.startResolutionForResult(
                            this@OsmActivity, 1001
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        handleLocationRejection()
                    }
                }

                else {
                    handleLocationRejection()
                }
            }
    }

    private fun startLocationUpdates(locationRequest: LocationRequest) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(hasil: LocationResult) {
                super.onLocationResult(hasil)
                for (location in hasil.locations) {
                    val cityName = getCityName(location)
                }
            }
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestLocationPermission()
        }
    }

    private fun handleLocationRejection() {

    }

    fun getCityName(location : Location) : String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        setUserLocation()
        Log.d("cityName", addresses.toString())
        return if (!addresses.isNullOrEmpty()) {
            var cityName = addresses[0].subAdminArea
            cityName = cityName?.replace("Kota ", "")?.replace("Kabupaten ", "")
            kota = cityName
            Log.d("kota", cityName)
            cityName
        } else {
            null
        }
    }

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if(!isShow) getLocationAndProceed()
        }
    }
    private fun requestLocationPermission() {
        requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}