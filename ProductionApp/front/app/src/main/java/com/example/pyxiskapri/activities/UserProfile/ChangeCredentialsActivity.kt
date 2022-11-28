package com.example.pyxiskapri.activities.UserProfile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.HomeActivity
import com.example.pyxiskapri.dtos.request.ChangePasswordRequest
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.utility.ActivityTransferStorage
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_change_credentials.*
import kotlinx.android.synthetic.main.activity_new_user_profile.*
import kotlinx.android.synthetic.main.activity_new_user_profile.shapeableImageView
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.et_first_name
import kotlinx.android.synthetic.main.activity_user_profile.tv_name1
import kotlinx.android.synthetic.main.activity_user_profile.tv_name2
import kotlinx.android.synthetic.main.modal_change_pass.*
import kotlinx.android.synthetic.main.modal_confirm_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ChangeCredentialsActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    private val PICK_IMAGE_CODE=1
    lateinit var profileImage: Uri
    lateinit var oldProfileImage:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_credentials)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)


        var cover_image = ActivityTransferStorage.coverImage
        var imageBitmap = android.util.Base64.decode(cover_image, android.util.Base64.DEFAULT)
        coverImage_c.setImageBitmap(BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap.size))

        setupGetUser()
        tv_change_pass.setOnClickListener(){
            changepass()
        }
        setupChangePhoto()
        saveChanges()

    }


    private fun setupGetUser() {

        val context:Context=this

        apiClient.getUserService(context).getUser()
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        et_first_name_n.setText(response.body()!!.firstName)
                        et_last_name_n.setText(response.body()!!.lastName)

                        et_username_n.setText(response.body()!!.username)

                        et_email_n.setText(response.body()!!.email)

                        tv_name1.text=response.body()!!.firstName
                        tv_name2.text=response.body()!!.lastName

                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            oldProfileImage=response.body()!!.profileImage
                            var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
                            shapeableImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
                        }


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong, try again.", Toast.LENGTH_LONG).show()
                }

            })
    }



    private fun saveChanges() {
        btn_save_changes.setOnClickListener()
        {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.modal_confirm_password)

            dialog.btn_confirm_password.setOnClickListener() {


                var slika = ""

                if (this::profileImage.isInitialized) {
                    var bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, profileImage);
                    var outputStream = ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    var byteArray = outputStream.toByteArray();
                    var encodedString =
                        android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
                    slika = encodedString
                } else if (this::oldProfileImage.isInitialized) {
                    slika = oldProfileImage
                }


                var editUserRequest = EditUserRequest(

                    firstName = this.et_first_name_n.text.toString(),
                    lastName = this.et_last_name_n.text.toString(),
                    username = this.et_username_n.text.toString(),
                    email = this.et_email_n.text.toString(),
                    password = dialog.et_modul_password.text.toString(),
                )


                val context: Context = this

                apiClient.getUserService(context).editUserData(editUserRequest)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("", response.body()?.token.toString())
                                sessionManager.clearToken()
                                sessionManager.saveToken(response.body()?.token.toString())

                                Toast.makeText(
                                    context,
                                    "Credentials changed successfully!",
                                    Toast.LENGTH_LONG
                                ).show()

                                val intent = Intent(context, NewUserProfileActivity::class.java)
                                startActivity(intent)

                            }

                            if (response.code() == Constants.CODE_BAD_REQUEST)
                                Toast.makeText(
                                    context,
                                    "The password is wrong, or username is taken!",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                context,
                                "Something went wrong, try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    })



                dialog.dismiss()
            }
            dialog.show()
        }
    }


    private fun changepass() {
        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_change_pass)

        dialog.show()
        dialog.btn_save_new_pass.setOnClickListener(){

            if(dialog.et_new_pass.text.toString()!=dialog.et_confirm_new_pass.text.toString())
                Toast.makeText(this,"New password and confirmed password are not the same!",Toast.LENGTH_LONG).show()

            else
            {
                var changepassreq= ChangePasswordRequest(
                    oldPassword = dialog.et_old_pass.text.toString(),
                    newPassword = dialog.et_new_pass.text.toString()
                )

                Log.d("",changepassreq.toString())

                val context: Context = this
                apiClient.getUserService(context).changePassword(changepassreq).enqueue(object:Callback<MessageResponse>{
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(context,"Password changed successfully!",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }

                        else
                            Toast.makeText(context,"Wrong password, try again!",Toast.LENGTH_LONG).show()

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Toast.makeText(context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                    }

                })

            }


        }

    }

    private fun setupChangePhoto() {


        tv_change_picture.setOnClickListener(){

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

                shapeableImageView.setImageURI(profileImage)

            }

        }

    }


}