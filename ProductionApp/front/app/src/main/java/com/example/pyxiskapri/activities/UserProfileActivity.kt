package com.example.pyxiskapri.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.modal_confirm_password.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UserProfileActivity : AppCompatActivity() {


    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    private val PICK_IMAGE_CODE=1
    lateinit var profileImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        setupChangePhoto()
        setupUpdateUser()

    }

    private fun setupGetUser() {
        apiClient.getUserService(this).getUser(sessionManager.fetchUserData()!!.username)
            .enqueue(object : retrofit2.Callback<GetUserResponse>{
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        tv_first_name.text=response.body()!!.firstName
                        tv_last_name.text=response.body()!!.lastName
                        tv_username.text=response.body()!!.username
                        tv_email.text=response.body()!!.email

                        tv_name1.text=response.body()!!.firstName
                        tv_name2.text=response.body()!!.lastName

                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun setupUpdateUser() {
        ib_update.setOnClickListener()
        {

            et_first_name.setText(tv_first_name.text)
            et_last_name.setText(tv_last_name.text)
            et_username.setText(tv_username.text)
            et_email.setText(tv_email.text)


            tv_first_name.isGone=true
            tv_last_name.isGone=true
            tv_username.isGone=true
            tv_email.isGone=true

            et_first_name.isGone=false
            et_last_name.isGone=false
            et_username.isGone=false
            et_email.isGone=false

            ib_update.isGone=true
            ib_confirm.isGone=false

            ib_confirm.setOnClickListener()
            {

                showModalDialog()


                tv_first_name.text=et_first_name.text.toString()
                tv_last_name.text=et_last_name.text.toString()
                tv_username.text=et_username.text.toString()
                tv_email.text=et_email.text.toString()

                tv_name1.text=et_first_name.text.toString()
                tv_name2.text=et_last_name.text.toString()

                tv_first_name.isGone=false
                tv_last_name.isGone=false
                tv_username.isGone=false
                tv_email.isGone=false

                et_first_name.isGone=true
                et_last_name.isGone=true
                et_username.isGone=true
                et_email.isGone=true

                ib_update.isGone=false
                ib_confirm.isGone=true

            }


        }
    }

    private fun showModalDialog() {


        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_confirm_password)


        dialog.btn_confirm_password.setOnClickListener(){

            EditUser()

            dialog.dismiss()
        }
        dialog.show()

    }

    private fun EditUser() {

        var editUserRequest= EditUserRequest(

            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            username = et_username.text.toString(),
            email = et_email.text.toString(),
            password = et_modul_password.text.toString()
        )

        val context: Context = this
        apiClient.getUserService(context).editUser(editUserRequest).enqueue(object :
            retrofit2.Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){

                    Toast.makeText(context, "User informations successfully updated", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(context, "Incorrect password, try again!", Toast.LENGTH_SHORT).show()
            }


        })

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
