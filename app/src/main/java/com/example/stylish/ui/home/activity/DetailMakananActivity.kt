package com.example.stylish.ui.home.activity

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylish.R
import com.example.stylish.adapter.FoodAdapter
import com.example.stylish.data.model.Menu
import com.example.stylish.data.model.MenuOrderRequest
import com.example.stylish.data.model.OrderRequest
import com.example.stylish.databinding.ActivityDetailMakananBinding
import com.example.stylish.databinding.KeranjangBottomSheetBinding
import com.example.stylish.models.Food
import com.example.stylish.ui.osm.OsmActivity
import com.example.stylish.viewmodel.LocationViewModel
import com.example.stylish.viewmodel.OrderViewModel
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailMakananActivity : AppCompatActivity() {
    private var _binding: ActivityDetailMakananBinding? = null
    private val binding get() = _binding
    private val locatioViewModel: LocationViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()
    private var isShow = false
    private var isLocationDialogShown = false
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var kota: String
    private var latitude: Double? = null
    private var longtitude: Double? = null
    private var lokasi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailMakananBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setData()
        binding?.keranjangButton?.setOnClickListener {
            showBottomSheet()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if(!isShow) getLocationAndProceed()

    }

    private fun showBottomSheet() {
        val menu = intent.getParcelableExtra<Menu>("menu")
        var pcsVal = 1
        Log.d("data_detail_menu", menu.toString())

        val sheetDialog =
            BottomSheetDialog(this@DetailMakananActivity, R.style.CustomBottomSheetDialogTheme)
        val sheetBinding = KeranjangBottomSheetBinding.inflate(layoutInflater)

        sheetBinding.foodName.text = menu?.name.toString()

        sheetBinding.plusButton.setOnClickListener {
            pcsVal++
            sheetBinding.stokValueTextView.text = pcsVal.toString()
        }

        sheetBinding.minButton.setOnClickListener {
            if (pcsVal > 1) {
                pcsVal--
                sheetBinding.stokValueTextView.text = pcsVal.toString()
            }
        }



            sheetBinding.keranjangButton.setOnClickListener {
                val catatan = sheetBinding.catatnEditText.text.toString()
                if (menu != null) {
                    val payload = OrderRequest(
                        merchant_id = menu.merchants.id,
                        location = lokasi!!,
                        menu = listOf(
                            MenuOrderRequest(
                                id = menu.id,
                                stok = pcsVal,
                                catatan = catatan
                            )
                        ),
                        latitude!!,
                        longtitude!!
                    )
                Log.d("keranjang_payload", payload.toString())
                Log.d("keranjang_payload", catatan)

                    orderViewModel.createOrderUser(payload, onSuccess = {response ->
                        Toast.makeText(this@DetailMakananActivity, "success", Toast.LENGTH_SHORT).show()
                    }, onError = {response ->
                        Toast.makeText(this@DetailMakananActivity, "error", Toast.LENGTH_SHORT).show()

                    })
            }
        }

        sheetDialog.apply {
            setContentView(sheetBinding.root)
            show()
        }
    }

    fun setData() {
        val menu = intent.getParcelableExtra<Menu>("menu")
        Log.d("data_detail_menu", menu.toString())

        menu.let {
            binding?.foodTitle?.text = menu?.name
            binding?.foodLocation?.text = menu?.merchants?.address
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
                        this@DetailMakananActivity, 1001
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
        latitude = location.latitude
        longtitude = location.longitude
        Log.d("longtitude", longtitude.toString())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        Log.d("cityName", addresses.toString())
        return if (!addresses.isNullOrEmpty()) {
            var cityName = addresses[0].subAdminArea
            val lokasiSaatIni = addresses[0].getAddressLine(0).substringAfter(",").trim()
            locatioViewModel.setLokasi(lokasiSaatIni)
            cityName = cityName?.replace("Kota ", "")?.replace("Kabupaten ", "")
            kota = cityName
            Log.d("kota", cityName)
            Log.d("lokasiSaatIni", lokasiSaatIni)
            lokasi = lokasiSaatIni
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
}