package com.example.pyxiskapri.activities

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
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.ChangePasswordRequest
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.modal_change_pass.*
import kotlinx.android.synthetic.main.modal_confirm_password.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.util.Objects

class UserProfileActivity : AppCompatActivity() {


    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    private val PICK_IMAGE_CODE=1
    lateinit var profileImage: Uri
    lateinit var oldProfileImage:String

    private var flag=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        setupChangePhoto()
        setupUpdateUser()
        confirmButton()

        back.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btn_change_pass.setOnClickListener()
        {
            changepass()
        }



    }


    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_drawerNavUserProfile.getFragment<DrawerNav>().showDrawer()
    }

    private fun setupGetUser() {
        apiClient.getUserService(this).getUser()
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

                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                        {
                            oldProfileImage=response.body()!!.profileImage
                            var imageData = android.util.Base64.decode(picture, android.util.Base64.DEFAULT)
                            imageViewReal.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.size))
                        }


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

            flag=1


            ib_confirm.isInvisible=false

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

    }


    }

    private fun changepass() {
        val dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.modal_change_pass)

        dialog.show()
        dialog.btn_save_new_pass.setOnClickListener(){

            if(et_new_pass.text.toString()!=et_confirm_new_pass.text.toString())
                Toast.makeText(this,"New password and confirmed password are not the same!",Toast.LENGTH_LONG).show()

            else
            {
                var changepassreq= ChangePasswordRequest(
                    oldPassword = et_old_pass.text.toString(),
                    newPassword = et_new_pass.text.toString()
                )

                apiClient.getUserService(this).changePassword(changepassreq).enqueue(object:Callback<MessageResponse>{
                    override fun onResponse(
                        call: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(dialog.context,"Password changed successfully!",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }

                        else
                            Toast.makeText(dialog.context,response.body()!!.message,Toast.LENGTH_LONG).show()

                    }

                    override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                        Toast.makeText(dialog.context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                    }

                })

            }


        }

    }

    private fun confirmButton()
    {
        ib_confirm.setOnClickListener()
        {

            val dialog= Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.modal_confirm_password)

            dialog.btn_confirm_password.setOnClickListener(){


                var slika=""

                if(this::profileImage.isInitialized) {
                    var bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, profileImage);
                    var outputStream = ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    var byteArray = outputStream.toByteArray();
                    var encodedString =
                        android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
                    slika=encodedString
                }

                else if(this::oldProfileImage.isInitialized)
                {
                   slika=oldProfileImage
                }

                if(flag==0)
                {
                    et_first_name.setText(tv_first_name.text)
                    et_last_name.setText(tv_last_name.text)
                    et_username.setText(tv_username.text)
                    et_email.setText(tv_email.text)
                }

                var editUserRequest= EditUserRequest(

                    firstName = this.et_first_name.text.toString(),
                    lastName = this.et_last_name.text.toString(),
                    username = this.et_username.text.toString(),
                    email = this.et_email.text.toString(),
                    password = dialog.et_modul_password.text.toString(),
                    profileimage = slika
                )


                val context: Context = this

                apiClient.getUserService(context).editUser(editUserRequest).enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            Log.d("",response.body()?.token.toString())
                            sessionManager.clearToken()
                            sessionManager.saveToken(response.body()?.token.toString())

                            Toast.makeText(context,"Credentials changed successfully!",Toast.LENGTH_LONG).show()

                            tv_first_name.text=et_first_name.text.toString()
                            tv_last_name.text=et_last_name.text.toString()
                            tv_username.text=et_username.text.toString()
                            tv_email.text=et_email.text.toString()

                            tv_name1.text=et_first_name.text.toString()
                            tv_name2.text=et_last_name.text.toString()

                        }

                        if(response.code() == Constants.CODE_NOT_FOUND)
                            Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show()
                        if(response.code() == Constants.CODE_BAD_REQUEST)
                            Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show()

                        flag=0
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                    }


                })



                dialog.dismiss()
            }
            dialog.show()

            ib_confirm.isInvisible=true

            tv_first_name.isGone=false
            tv_last_name.isGone=false
            tv_username.isGone=false
            tv_email.isGone=false

            et_first_name.isGone=true
            et_last_name.isGone=true
            et_username.isGone=true
            et_email.isGone=true

            ib_update.isGone=false



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
                ib_confirm.isInvisible=false

            }

        }

    }

}
