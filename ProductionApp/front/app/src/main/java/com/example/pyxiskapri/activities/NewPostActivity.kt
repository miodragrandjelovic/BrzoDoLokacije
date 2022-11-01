package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Context
import android.content.Intent

import android.net.Uri
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.ImageGridAdapter
import com.example.pyxiskapri.adapters.LocationListAdapter
import com.example.pyxiskapri.models.ImageGridItem


import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.LocationResponse
import com.example.pyxiskapri.dtos.response.MessageResponse

import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_new_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

import java.util.*


class NewPostActivity : AppCompatActivity(){
    private val PICK_IMAGES_CODE = 0
    private val PICK_COVER_IMAGE_CODE = 1

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    lateinit var locationListAdapter: LocationListAdapter
    lateinit var imageGridAdapter: ImageGridAdapter

    private var selectedLocationID: Int = -1
    lateinit var coverImage:Uri
    var images:ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setupImageGridAdapter()
        setupLocationListAdapter()

        setupSearchLocationButton()
        setupPickCoverImage()
        setupPickImagesButton()

        setupAddPost()

    }

    private fun onLocationListItemClick(id: Int, name: String){
        selectedLocationID = id
        tv_selectedLocation.text = name
    }

    private fun setupSearchLocationButton() {
        var context: Context = this
        btn_searchLocation.setOnClickListener {
            apiClient.getPlaceService(context)
                .getLocationsByString(et_location.text.toString().trim())
                .enqueue(object : Callback<MutableList<LocationResponse>> {

                    override fun onResponse(
                        call: Call<MutableList<LocationResponse>>,
                        response: Response<MutableList<LocationResponse>>
                    ) {
                        if(response.isSuccessful)
                            locationListAdapter.setLocations(response.body()!!)
                    }

                    override fun onFailure(
                        call: Call<MutableList<LocationResponse>>,
                        t: Throwable
                    ) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }

            /*apiClient.getUserService(context).login(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(response.isSuccessful) {
                            sessionManager.saveToken(response.body()?.token.toString())
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                        }

                        if(response.code() == Constants.CODE_NOT_FOUND)
                            Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show()
                        if(response.code() == Constants.CODE_BAD_REQUEST)
                            Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                })*/


    private fun setupLocationListAdapter(){
        locationListAdapter = LocationListAdapter(mutableListOf()) { id, name -> onLocationListItemClick(id, name) }
        rv_locations.adapter = locationListAdapter
        rv_locations.layoutManager = LinearLayoutManager(this)

    }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    /*
    private fun drawableToByteArray(d:Drawable):ByteArray{
        val bitmap = (d as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }


     */

    private fun setupAddPost() {
         btn_addPost.setOnClickListener()
        {
            if(tv_selectedLocation.text.toString().trim() != "" && selectedLocationID >= 0 && et_description.text.toString().trim() !="" && iv_coverImage.drawable!=null)
                addPost()
            else
                Toast.makeText(this, "You have to fill all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addPost() {

        for(imageGridItem in imageGridAdapter.imageItems)
        {
            var byteArray=readBytes(this,imageGridItem.uri)
            images.add(byteArray!!.toString())
        }


        var byteCoverImage=readBytes(this,coverImage)


        var newPostRequest= NewPostRequest(
            location = selectedLocationID,
            description = et_description.text.toString(),
            coverImage= byteCoverImage!!.toString(),
            images=images,
        )

        val context: Context = this
        apiClient.getPostService(context).addPost(newPostRequest).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Multiple images chosen
                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (index in 0 until count) {
                        imageGridAdapter.imageItems!!.add(
                            ImageGridItem(
                                data.clipData!!.getItemAt(
                                    index
                                ).uri
                            )
                        )
                    }
                }

                // Single image chosen
                else {
                    val imageUri = data.data
                    if (imageUri != null)
                        imageGridAdapter.imageItems!!.add(ImageGridItem(imageUri))
                }

            }

        }
            else  {

                    if (data!!.clipData != null) {
                        //     val count = data.clipData!!.itemCount
                        //      for(index in 0 until count){
                        iv_coverImage.setImageURI(data.clipData!!.getItemAt(0).uri)

                        coverImage=data.clipData!!.getItemAt(0).uri

                        //    }
                    }

                    // Single image chosen
                    else {
                        val imageUri = data.data
                        if (imageUri != null) {
                            iv_coverImage.setImageURI(imageUri)
                            coverImage=imageUri
                        }
                    }


                Log.d("",iv_coverImage.drawable.toString())

                tv_coverText.isVisible = false
                btn_coverImage.isVisible = false

            }


        imageGridAdapter.notifyDataSetChanged()
    }

}