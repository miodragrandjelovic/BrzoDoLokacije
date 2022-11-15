package com.example.pyxiskapri.activities


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.ImageGridAdapter
import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.ImageGridItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.btn_home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class NewPostActivity : AppCompatActivity(), OnMapReadyCallback{
    private val PICK_IMAGES_CODE: Int = 0
    private val PICK_COVER_IMAGE_CODE: Int = 1

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder
    private var locationList: ArrayList<String> = ArrayList<String>()


    private lateinit var selectedLocation: LatLng
    private lateinit var locationAddress: String
    private lateinit var locationCity: String
    private lateinit var locationCountry: String

    private lateinit var coverImage:Uri
    private lateinit var imageGridAdapter: ImageGridAdapter
    var images:ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupMap()
        setupLocationButton()

        setupImageGridAdapter()
        setupPickCoverImage()
        setupPickImagesButton()

        setupGoHomeButton()

        setupAddPostButton()

    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavNewPost.getFragment<DrawerNav>().showDrawer()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun setupGoHomeButton(){
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }
    }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? = context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    private fun setupAddPostButton() {
        btn_addPost.setOnClickListener {
            for (imageGridItem in imageGridAdapter.imageItems) {
                val byteArray = readBytes(this, imageGridItem.uri)
                images.add(byteArray!!.toString())
            }
            var bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), coverImage);
            var outputStream = ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            var byteArray = outputStream.toByteArray();
            var encodedCoverImage = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);

            val newPostRequest = NewPostRequest(
                description = et_description.text.toString(),
                address = locationAddress,
                locationName = "__LocationName__",
                city = locationCity,
                country = locationCountry,
                longitude = selectedLocation.longitude,
                latitude = selectedLocation.latitude,
                coverImage = encodedCoverImage,
                images = images
            )

            sendPostRequest(newPostRequest, this)
        }
    }

    private fun sendPostRequest(newPostRequest: NewPostRequest, context: Context){
        apiClient.getPostService(context).addPost(newPostRequest).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(context, "Adding new post failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }





    private fun setupImageGridAdapter() {
        imageGridAdapter = ImageGridAdapter(mutableListOf(), this)
        gv_additionalImages.adapter = imageGridAdapter
    }

    private fun setupPickImagesButton() {
        btn_pickImages.setOnClickListener {
            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE)
        }
    }

    private fun setupPickCoverImage() {

        btn_coverImage.setOnClickListener(){
                val intent: Intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Cover Images"), PICK_COVER_IMAGE_CODE)
            }

        cl_coverImage.setOnClickListener(){
            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Cover Images"), PICK_COVER_IMAGE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Multiple images chosen
                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (index in 0 until count)
                        imageGridAdapter.imageItems.add(ImageGridItem(data.clipData!!.getItemAt(index).uri))
                }

                // Single image chosen
                else {
                    val imageUri = data.data
                    if (imageUri != null)
                        imageGridAdapter.imageItems.add(ImageGridItem(imageUri))
                }

            }
            imageGridAdapter.notifyDataSetChanged()
        }

        if(requestCode == PICK_COVER_IMAGE_CODE)  {
            if(data == null)
                return

            if (data.clipData != null) {
                iv_coverImage.setImageURI(data.clipData!!.getItemAt(0).uri)
                coverImage=data.clipData!!.getItemAt(0).uri
            }
            else {
                val imageUri = data.data
                if (imageUri != null) {
                    iv_coverImage.setImageURI(imageUri)
                    coverImage=imageUri
                }
            }

            tv_coverText.isVisible = false
            btn_coverImage.isVisible = false

        }
    }





    private fun setupMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        geocoder = Geocoder(this, Locale.getDefault())
        setupAddMarkerOnTouch()
    }

    private fun addMarker(address: Address){

        if(address.countryName == null || address.locality == null || address.featureName == null && address.thoroughfare == null)
            return

        val location: LatLng = LatLng(address.latitude, address.longitude)
        map.clear()
        map.addMarker(MarkerOptions().position(location))

        val locationName: String = address.getAddressLine(0)
        btn_location.text = locationName
        selectedLocation = location

        tv_latitude.text = buildString {
            append("Lat: ")
            append(location.latitude.toString().take(12))
        }
        tv_longitude.text = buildString {
            append("Long: ")
            append(location.longitude.toString().take(12))
        }

        locationAddress = buildString {
            append(address.thoroughfare)
            append(" ")
            append(address.featureName)
        }

        locationCity = address.locality
        locationCountry = address.countryName

        Log.d("Address", address.toString())
    }

    private fun setupAddMarkerOnTouch(){
        map.setOnMapClickListener {
            val addresses : MutableList<Address>? = geocoder.getFromLocation(it.latitude, it.longitude, 1)

            if(addresses == null || addresses.size == 0)
                return@setOnMapClickListener

            addMarker(addresses[0])
        }
    }

    private fun setupLocationButton() {
        btn_location.setOnClickListener{
            val dialog: Dialog = Dialog(this)

            dialog.setContentView(R.layout.dialog_location_search)
            dialog.window?.setLayout(920, 1000)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            var editText: EditText = dialog.findViewById(R.id.edit_text)
            var listView: ListView =dialog.findViewById(R.id.list_view)

            var listAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.item_search_text, locationList)

            var fetchedAddresses: MutableList<Address> = mutableListOf()

            listView.adapter = listAdapter
            editText.doOnTextChanged { _, _, _, _ ->
                listAdapter.clear()

                if(editText.text.toString() != "") {
                    fetchedAddresses = geocoder.getFromLocationName(editText.text.toString(), 6)!!
                    for (address in fetchedAddresses)
                        listAdapter.add(address.getAddressLine(0))
                }


                listAdapter.notifyDataSetChanged()
            }

            listView.onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    addMarker(fetchedAddresses[position])
                    dialog.dismiss()
                }

        }
    }
}