package bmt.android.com.bookmytractor.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.location.LocationManager
import android.nfc.Tag
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.model.MachinesListPojo.Machine
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivityCropListBinding
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.helper.NpaGridlayoutManager
import bmt.android.com.bookmytractor.ui.adapters.CropListAdapter
import bmt.android.com.bookmytractor.ui.adapters.MachineListClickListener
import bmt.android.com.bookmytractor.ui.adapters.MachinesListAdapter
import bmt.android.com.bookmytractor.ui.login.CreateAccountActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MachineListActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    //Location parameters
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    lateinit var locationManager: LocationManager
    private val TAG: String = MachineListActivity::class.java.simpleName
    private var activityCropListBinding: ActivityCropListBinding? = null
    private var machinesListAdapter: MachinesListAdapter? = null
    private var disposable: Disposable? = null
    private var customProgressBar: CustomProgressBar? = null
    private var userPrefs: UserPrefs? = null
    private val machineList = ArrayList<Machine>()
    private var offset: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCropListBinding = DataBindingUtil.setContentView(this, R.layout.activity_crop_list)

        customProgressBar = CustomProgressBar(this)
        userPrefs = UserPrefs(this)
        //  customProgressBar!!.showProgressBar("Fetching Machines list")

        machinesListAdapter = MachinesListAdapter(machineList, applicationContext, object : MachineListClickListener {
            override fun onBtnClick(position: Int, frameLayout: FrameLayout) {
                startActivity(Intent(applicationContext, MachineListActivity::class.java))
            }
        })

        //now adding the adapter to recyclerview
        activityCropListBinding!!.cropListRecycler.adapter = machinesListAdapter

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkLocation() //check whether location service is enable or not in your  phone
    }


    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()

    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode())
    }

    override fun onLocationChanged(location: Location) {


        val msg = "Updated Location: Latitude " + location.longitude.toString() + location.longitude
//        txt_latitude.setText(""+location.latitude);
//        txt_longitude.setText(""+location.longitude);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


    }

    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return
        }

        startLocationUpdates()

        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.getLastLocation()

        Log.d(TAG, "lat is:" + fusedLocationProviderClient.lastLocation.result.latitude)


    }


    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage(R.string.gps_disabled_caution)
                .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    private fun fetchMachinesList() {
        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getMachinesList(12, "", 1)!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            if (result.success!!) {
                                customProgressBar!!.hideProgress()
                                machineList.addAll(result.machines!!)
                                activityCropListBinding!!.cropListRecycler.layoutManager = LinearLayoutManager(this)
                                machinesListAdapter?.notifyItemRangeChanged(0, machineList.size)
                                offset++
                            }
                        }
                ) { error ->
                    customProgressBar!!.hideProgress()
                    CommUtils(this).showToastMessage(error.message!!)
                }
    }

}