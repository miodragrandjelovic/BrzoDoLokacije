package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.activity_new_post.*


class NewPostActivity : AppCompatActivity() {

    private var images: ArrayList<Uri?>? = null

    private var position=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        images = ArrayList()

        imageSwitcher.setFactory { ImageView(applicationContext) }

        val animation: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        imageSwitcher.inAnimation = animation

        pickImagesBtn.setOnClickListener(){
            pickImagesIntent()
        }

        previousBtn.setOnClickListener(){
            if(position>0)
            {
                position--
                imageSwitcher.setImageURI(images!![position])

                if(position==0)
                    previousBtn.isVisible=false

                if(position<images!!.size-1)
                    nextBtn.isVisible=true
            }
        }

        nextBtn.setOnClickListener(){
            if(position<images!!.size-1)
            {
                position++
                imageSwitcher.setImageURI(images!![position])

                if(position>0)
                    previousBtn.isVisible=true

                if(position==images!!.size-1)
                    nextBtn.isVisible=false
            }
        }

        deleteBtn.setOnClickListener(){

            images!!.removeAt(position)

            if(position>0)
            {
                position--
            }
            if(images!!.size>0)
                imageSwitcher.setImageURI(images!![position])
            else
            {
                imageSwitcher.setImageURI(null)
                deleteBtn.isVisible=false
                previousBtn.isVisible=false
                nextBtn.isVisible=false
            }


        }


    }


    fun pickImagesIntent(){
        val intent:Intent=Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action=Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data

            if (data!!.clipData != null) {
                //images!!.clear()
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images!!.add(images!!.size,imageUri)
                }
            } else {
                val imageUri = data.data
                images!!.add(images!!.size,imageUri)

            }

            imageSwitcher.setImageURI(images!![0])
            position = 0

            deleteBtn.isVisible=true

            previousBtn.isVisible=false
            if(images!!.size<2)
                nextBtn.isVisible=false
            else
                nextBtn.isVisible=true


        }

    }
}