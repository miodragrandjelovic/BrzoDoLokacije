package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.NewPostRequest
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_new_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date


class NewPostActivity : AppCompatActivity() {

    private var images: ArrayList<Uri?>? = null

    private var position=0

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        setupAddPost()

        images = ArrayList()

        imageSwitcher.setFactory { ImageView(applicationContext) }
        val animation: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        imageSwitcher.inAnimation = animation


        sessionManager = SessionManager(this)
        apiClient = ApiClient()


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
                noImage.isVisible=true
                deleteBtn.isVisible=false
                previousBtn.isVisible=false
                nextBtn.isVisible=false
            }
            if(images!!.size==1)
            {
                previousBtn.isVisible=false
                nextBtn.isVisible=false
            }
            else if(images!!.size==2)
            {
                previousBtn.isVisible=false
                nextBtn.isVisible=true
            }
            else if(images!!.size==3)
            {
                previousBtn.isVisible=true
                nextBtn.isVisible=true
            }


        }


    }

    private fun setupAddPost() {
        addPostBtn.setOnClickListener()
        {
            if(et_location.text.toString()!="" && et_description.text.toString()!="" && images!=null)
                addPost()
            else
                Toast.makeText(this, "You have to fill all the fields!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addPost() {

        val sdf=SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val user=sessionManager.fetchUserData()

        var newPostRequest=NewPostRequest(
            location = et_location.text.toString(),
            description = et_description.text.toString(),
            images=images!!,
            dateAndTime = currentDate,
            user=user!!
        )

        val context: Context = this

        apiClient.getUserService(context).addPost(newPostRequest).enqueue(object :
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


    fun pickImagesIntent(){
        val intent:Intent=Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action=Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            noImage.isVisible=false

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