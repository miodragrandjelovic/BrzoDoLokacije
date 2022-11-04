package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {


    private val PICK_IMAGE_CODE=1
    lateinit var profileImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setupChangePhoto()
        setupChangeFirstName()
        setupChangeLastName()
        setupChangeUsername()
        setupChangeEmail()

    }

    private fun setupChangeEmail() {

        ib_email_edit.setOnClickListener(){
            //var currentName= tv_first_name.text.toString()

            et_email.text.clear()

            tv_email.isGone=true

            et_email.isGone=false

            ib_email_edit.isGone=true

            ib_email_confirm.isGone=false


            ib_email_confirm.setOnClickListener(){
                var newName = et_email.text.toString()
                tv_email.text=newName

                tv_email.isGone=false
                et_email.isGone=true
                ib_email_edit.isGone=false
                ib_email_confirm.isGone=true
            }


        }


    }

    private fun setupChangeUsername() {
        ib_username_edit.setOnClickListener(){
            //var currentName= tv_first_name.text.toString()

            et_username.text.clear()

            tv_username.isGone=true

            et_username.isGone=false

            ib_username_edit.isGone=true

            ib_username_confirm.isGone=false


            ib_username_confirm.setOnClickListener(){
                var newName = et_username.text.toString()
                tv_username.text=newName

                tv_username.isGone=false
                et_username.isGone=true
                ib_username_edit.isGone=false
                ib_username_confirm.isGone=true
            }


        }
    }

    private fun setupChangeLastName() {
        ib_last_name_edit.setOnClickListener(){
            //var currentName= tv_first_name.text.toString()

            et_last_name.text.clear()

            tv_last_name.isGone=true

            et_last_name.isGone=false

            ib_last_name_edit.isGone=true

            ib_last_name_confirm.isGone=false


            ib_last_name_confirm.setOnClickListener(){
                var newName = et_last_name.text.toString()
                tv_last_name.text=newName

                tv_last_name.isGone=false
                et_last_name.isGone=true
                ib_last_name_edit.isGone=false
                ib_last_name_confirm.isGone=true
            }


        }
    }

    private fun setupChangeFirstName() {

        ib_first_name_edit.setOnClickListener(){
            //var currentName= tv_first_name.text.toString()

            et_first_name.text.clear()

            tv_first_name.isGone=true

            et_first_name.isGone=false

            ib_first_name_edit.isGone=true

            ib_first_name_confirm.isGone=false


            ib_first_name_confirm.setOnClickListener(){
                var newName = et_first_name.text.toString()
                tv_first_name.text=newName

                tv_first_name.isGone=false
                et_first_name.isGone=true
                ib_first_name_edit.isGone=false
                ib_first_name_confirm.isGone=true
            }


        }
    }




    private fun setupChangePhoto() {

        ib_change_photo.setOnClickListener(){

            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data!!.clipData != null) {

                    profileImage = data.clipData!!.getItemAt(0).uri
                } else {
                    val imageUri = data.data
                    if (imageUri != null) {

                        profileImage = imageUri
                    }
                }

                imageViewReal.setImageURI(profileImage)

            }

        }

    }

}
