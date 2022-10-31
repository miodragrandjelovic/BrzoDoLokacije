package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.ImageGridAdapter
import com.example.pyxiskapri.adapters.LocationListAdapter
import com.example.pyxiskapri.models.ImageGridItem
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_new_post.*

class NewPostActivity : AppCompatActivity() {

    private val PICK_IMAGES_CODE = 0

    lateinit var locationListAdapter: LocationListAdapter
    lateinit var imageGridAdapter: ImageGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        setupLocationListAdapter()

        setupImageGridAdapter()

        setupPickImagesButton()

        button2.setOnClickListener {
            locationListAdapter.removeLocation()
        }
        button3.setOnClickListener {
            locationListAdapter.addLocation()
        }

    }

    private fun setupLocationListAdapter(){
        locationListAdapter = LocationListAdapter(mutableListOf())
        rv_locations.adapter = locationListAdapter
        rv_locations.layoutManager = LinearLayoutManager(this)
    }

    private fun setupImageGridAdapter(){
        imageGridAdapter = ImageGridAdapter(mutableListOf(), this)
        gv_additionalImages.adapter = imageGridAdapter
    }

    private fun setupPickImagesButton(){
        btn_pickImages.setOnClickListener {
            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode != PICK_IMAGES_CODE)
            return

        if(resultCode == Activity.RESULT_OK){
            // Multiple images chosen
            if(data!!.clipData != null){
                val count = data.clipData!!.itemCount
                for(index in 0 until count){
                    imageGridAdapter.imageItems!!.add(ImageGridItem(data.clipData!!.getItemAt(index).uri))
                }
            }

            // Single image chosen
            else{
                val imageUri = data.data
                if(imageUri != null)
                    imageGridAdapter.imageItems!!.add(ImageGridItem(imageUri))
            }

        }

        imageGridAdapter.notifyDataSetChanged()
    }

}