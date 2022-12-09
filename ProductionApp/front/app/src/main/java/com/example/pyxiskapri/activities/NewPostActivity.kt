package com.example.pyxiskapri.activities


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.CoverPickImagesAdapter
import com.example.pyxiskapri.adapters.ImageGridAdapter
import com.example.pyxiskapri.adapters.ImageUploadProgressAdapter
import com.example.pyxiskapri.adapters.TagsInputAdapter
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.models.ImageGridItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.btn_discover
import kotlinx.android.synthetic.main.activity_new_post.btn_home
import kotlinx.android.synthetic.main.activity_new_post.btn_messages
import kotlinx.android.synthetic.main.activity_new_post.btn_newPost
import kotlinx.android.synthetic.main.dialog_images_upload_progress.*
import kotlinx.android.synthetic.main.dialog_location_search.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


@Suppress("DEPRECATION")
class NewPostActivity : AppCompatActivity(), OnMapReadyCallback{

    // API, SESIJE...
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    // MAP API, GEOCODER...
    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder


    // UI
    private var selectedCard: Int = 1

    // CARD LOCATION
    private val INVALID_LOCATION_VALUE : LatLng = LatLng(-123456789.0, -123456789.0)
    private var locationList: ArrayList<String> = ArrayList<String>()
    private var selectedLocation: LatLng = INVALID_LOCATION_VALUE
    private lateinit var locationName: String
    private lateinit var locationAddress: String
    private lateinit var locationCity: String
    private lateinit var locationCountry: String

    // CARD IMAGES
    private val PICK_IMAGES_CODE: Int = 0
    private lateinit var imageGridAdapter: ImageGridAdapter

    // CARD COVER
    private val PICK_COVER_IMAGE_CODE: Int = 1
    private lateinit var coverImage: Uri
    lateinit var coverPickImagesAdapter: CoverPickImagesAdapter
    private var selectedCoverIndex = 0

    // CARD POST
    lateinit var uploadProgressAdapter: ImageUploadProgressAdapter

    lateinit var locationTextListAdapter: ArrayAdapter<String>

    lateinit var tagsAdapter: TagsInputAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupMap()
        setupLocationButton()

        setupImageGridAdapter()
        setupPickImagesButton()

        setupPickCoverImageAdapter()
        setupTagsAdapter()

        setupNavButtons()

        setupCardUIButtons()
        updateCardUI()

        setupAddPostButton()
    }


    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavNewPost.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupNavButtons(){
        // NEW POST
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }

        // MAPS
        btn_discover.setOnClickListener {
            val intent = Intent (this, MapActivity::class.java);
            startActivity(intent);
        }

        // HOME
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }

        // MESSAGES
        btn_messages.setOnClickListener {
            val intent = Intent (this, ChatMainActivity::class.java);
            startActivity(intent);
        }

        // NOTIFICATIONS
    }

    // UI CONTROL

    private fun checkCurrentCardIsValid(cardNumber: Int): Boolean{
        when(cardNumber){
            1 -> {
                if(imageGridAdapter.imageItems.size > 0)
                    return true

            }

            2 -> {
                return true
            }

            3 -> {
                if(INVALID_LOCATION_VALUE != selectedLocation)
                    return true
            }

            4 -> {
                // TAG CHECK
                if(et_description != null)
                    return true
            }

        }
        return false
    }

    private fun setupCardUIButtons(){
        btn_cardBack.setOnClickListener{
            selectedCard--
            updateCardUI()
        }

        btn_cardNext.setOnClickListener {
            if(checkCurrentCardIsValid(selectedCard)) {
                selectedCard++
                updateCardUI()
            }
        }

        iv_circle1.setOnClickListener {
            selectedCard = 1
            updateCardUI()
        }
        iv_circle2.setOnClickListener {
            if(!checkCurrentCardIsValid(1))
                return@setOnClickListener

            selectedCard = 2
            updateCardUI()
        }
        iv_circle3.setOnClickListener {
            if(!checkCurrentCardIsValid(1) || !checkCurrentCardIsValid(2))
                return@setOnClickListener

            selectedCard = 3
            updateCardUI()
        }
        iv_circle4.setOnClickListener {
            if(!checkCurrentCardIsValid(1) || !checkCurrentCardIsValid(2) || !checkCurrentCardIsValid(3))
                return@setOnClickListener

            selectedCard = 4
            updateCardUI()
        }
    }

    private fun resetAllUIComponents(){
        val lightGray = Color.parseColor("#FF686F74")
        val barGray = Color.parseColor("#FF393D40")
        val darkGray = Color.parseColor("#FF1B1B1E")

        // Bars
        iv_bar1.setColorFilter(barGray)
        iv_bar2.setColorFilter(barGray)
        iv_bar3.setColorFilter(barGray)

        // 1
        iv_circle1.setImageResource(R.drawable.circle)
        iv_circle1.setColorFilter(lightGray)
        tv_1.setTextColor(darkGray)
        tv_circleLocation.setTextColor(lightGray)

        // 2
        iv_circle2.setImageResource(R.drawable.circle)
        iv_circle2.setColorFilter(lightGray)
        tv_2.setTextColor(darkGray)
        tv_circleCover.setTextColor(lightGray)

        // 3
        iv_circle3.setImageResource(R.drawable.circle)
        iv_circle3.setColorFilter(lightGray)
        tv_3.setTextColor(darkGray)
        tv_circleImages.setTextColor(lightGray)

        // 4
        iv_circle4.setImageResource(R.drawable.circle)
        iv_circle4.setColorFilter(lightGray)
        tv_4.setTextColor(darkGray)
        tv_circlePost.setTextColor(lightGray)

        // CARDS
        cl_locationCard.visibility = View.GONE
        cl_imagesCard.visibility = View.GONE
        cl_coverCard.visibility = View.GONE
        cl_postCard.visibility = View.GONE

        // BUTTONS
        btn_cardBack.visibility = View.GONE
        btn_cardNext.visibility = View.GONE
    }

    private fun turnOnCardIndicator(circle: ImageView, number: TextView, label: TextView){
        circle.setColorFilter(Color.parseColor("#FFCC2045"))
        number.setTextColor(Color.WHITE)
        label.setTextColor(Color.WHITE)
        tv_circleLocation.setTextColor(Color.WHITE)
    }


    private fun updateCardUI(){
        resetAllUIComponents()

        val red: Int = Color.parseColor("#FFCC2045")

        when(selectedCard){

            // LOCATION CARD
            1 -> {
                turnOnCardIndicator(iv_circle1, tv_1, tv_circleLocation)
                iv_circle1.setImageResource(R.drawable.circle_selected)
                btn_cardBack.visibility = View.VISIBLE
                btn_cardNext.visibility = View.VISIBLE
                cl_imagesCard.visibility = View.VISIBLE
            }

            // IMAGES CARD
            2 -> {
                turnOnCardIndicator(iv_circle1, tv_1, tv_circleLocation)
                turnOnCardIndicator(iv_circle2, tv_2, tv_circleImages)
                iv_circle2.setImageResource(R.drawable.circle_selected)
                iv_bar1.setColorFilter(red)
                btn_cardBack.visibility = View.VISIBLE
                btn_cardNext.visibility = View.VISIBLE
                updatePickCoverImagesRv()
                cl_coverCard.visibility = View.VISIBLE
            }

            // COVER CARD
            3 -> {
                turnOnCardIndicator(iv_circle1, tv_1, tv_circleLocation)
                turnOnCardIndicator(iv_circle2, tv_2, tv_circleCover)
                turnOnCardIndicator(iv_circle3, tv_3, tv_circleImages)
                iv_circle3.setImageResource(R.drawable.circle_selected)
                iv_bar1.setColorFilter(red)
                iv_bar2.setColorFilter(red)
                btn_cardNext.visibility = View.VISIBLE
                cl_locationCard.visibility = View.VISIBLE
            }

            // POST CARD
            4 -> {
                turnOnCardIndicator(iv_circle1, tv_1, tv_circleLocation)
                turnOnCardIndicator(iv_circle2, tv_2, tv_circleCover)
                turnOnCardIndicator(iv_circle3, tv_3, tv_circleImages)
                turnOnCardIndicator(iv_circle4, tv_4, tv_circlePost)
                iv_circle4.setImageResource(R.drawable.circle_selected)
                iv_bar1.setColorFilter(red)
                iv_bar2.setColorFilter(red)
                iv_bar3.setColorFilter(red)
                btn_cardBack.visibility = View.VISIBLE
                cl_postCard.visibility = View.VISIBLE
            }
        }
    }



    // MAP
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

        locationName = address.getAddressLine(0)
        locationCity = address.locality
        locationCountry = address.countryName

        btn_location.text = locationName

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
            dialog.window?.setLayout(1000, 1200)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            var editText: EditText = dialog.findViewById(R.id.edit_text)
            var listView: ListView = dialog.findViewById(R.id.list_view)

            var locationTextListAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.item_search_text, locationList)

            var fetchedAddresses: MutableList<Address> = mutableListOf()

            listView.adapter = locationTextListAdapter

            dialog.btn_searchLocations.setOnClickListener{
                locationTextListAdapter.clear()

                if(editText.text.toString() != "") {
                    fetchedAddresses = geocoder.getFromLocationName(editText.text.toString(), 8)!!
                    if (fetchedAddresses.size != 0) {
                        for (address in fetchedAddresses)
                            locationTextListAdapter.add(address.getAddressLine(0))

                        locationTextListAdapter.notifyDataSetChanged()
                    } else
                        Toast.makeText(this, "No locations found!", Toast.LENGTH_LONG).show()
                }

            }

            listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                    addMarker(fetchedAddresses[position])
                    dialog.dismiss()
            }

        }
    }




    // IMAGES + COVER IMAGE

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

//        if(requestCode == PICK_COVER_IMAGE_CODE)  {
//            if(data == null)
//                return
//
//            if (data.clipData != null) {
//                iv_coverImage.setImageURI(data.clipData!!.getItemAt(0).uri)
//                coverImage=data.clipData!!.getItemAt(0).uri
//            }
//            else {
//                val imageUri = data.data
//                if (imageUri != null) {
//                    iv_coverImage.setImageURI(imageUri)
//                    coverImage=imageUri
//                }
//            }
//
//        }
    }

    private fun setupImageGridAdapter() {
        imageGridAdapter = ImageGridAdapter(mutableListOf(), this, ::imageRemovedListener)
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

    private fun setupPickCoverImageAdapter(){
        coverPickImagesAdapter = CoverPickImagesAdapter(arrayListOf(), ::selectCoverImageListener)
        rv_images.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rv_images.adapter = coverPickImagesAdapter
        (rv_images.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }


    private fun selectCoverImageListener(selectedIndex: Int){
        selectedCoverIndex = selectedIndex
        iv_coverImage.setImageURI(imageGridAdapter.imageItems[selectedCoverIndex].uri)
    }

    private fun imageRemovedListener(removedIndex: Int){
        if(removedIndex == selectedCoverIndex)
            selectedCoverIndex = 0
        if(removedIndex < selectedCoverIndex)
            selectedCoverIndex--
    }

    private fun updatePickCoverImagesRv() {
        coverPickImagesAdapter.setImages(imageGridAdapter.imageItems, selectedCoverIndex)
        iv_coverImage.setImageURI(imageGridAdapter.imageItems[selectedCoverIndex].uri)
    }




    private fun setupTagsAdapter(){
        tagsAdapter = TagsInputAdapter(mutableListOf(), this)
        rv_tags.adapter = tagsAdapter
        rv_tags.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)

        btn_addTag.setOnClickListener {
            if(et_tagInput.text.trim() == "")
                return@setOnClickListener
            tagsAdapter.addTag(et_tagInput.text.toString())
            et_tagInput.text.clear()
        }
    }

    // UPLOAD POST

    private fun setupAddPostButton() {
        btn_addPost.setOnClickListener {

            if(!checkCurrentCardIsValid(selectedCard))
                return@setOnClickListener

            // Dialog Setup
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_images_upload_progress)
            dialog.window?.setLayout(920, 1000)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()

            // RV i Dialog Setup
            val uploadProgressAdapter = ImageUploadProgressAdapter(arrayListOf())
            dialog.rv_imagsUploadProgress.adapter = uploadProgressAdapter
            dialog.rv_imagsUploadProgress.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

            // Images Multipart.Body
            val partImagesList: ArrayList<MultipartBody.Part> = arrayListOf()
            var imageIndex = 0
            for (imageGridItem in imageGridAdapter.imageItems)
                partImagesList.add(UtilityFunctions.uriToProgressMultipartPart(this, imageIndex++, imageGridItem.uri, "Images", "postImage", uploadProgressAdapter.uploadListener))

            val pomItem = partImagesList[selectedCoverIndex]
            partImagesList[selectedCoverIndex] = partImagesList[0]
            partImagesList[0] = pomItem

            val context: Context = this
            // Images Upload
            apiClient.getPostService(this).addPost(
                Images = partImagesList,
                Description = UtilityFunctions.requestBodyFromString(et_description.text.toString()),
                Longitude = UtilityFunctions.requestBodyFromString(selectedLocation.longitude.toString()),
                Latitude = UtilityFunctions.requestBodyFromString(selectedLocation.latitude.toString()),
                LocationName = UtilityFunctions.requestBodyFromString(locationName),
                Address = UtilityFunctions.requestBodyFromString(locationAddress),
                City = UtilityFunctions.requestBodyFromString(locationCity),
                Country = UtilityFunctions.requestBodyFromString(locationCountry),
                Tags = UtilityFunctions.requestBodyFromString(tagsAdapter.tagsList.joinToString(separator = ", "))
            ).enqueue(
                object : Callback<MessageResponse> {
                    override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                        if (response.isSuccessful) {
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                            dialog.dismiss()
                            finish()
                        }
                        else{
                            Toast.makeText(context, "Adding new post failed (with response)!", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(context, "Adding new post failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }


}