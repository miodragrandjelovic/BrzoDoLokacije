package com.example.pyxiskapri.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.documentfile.provider.DocumentFile
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.UserPostsAdapter
import com.example.pyxiskapri.dtos.request.ChangePasswordRequest
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.modal_change_pass.*
import kotlinx.android.synthetic.main.modal_confirm_password.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI

class UserProfileActivity : AppCompatActivity() {


    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    private val PICK_IMAGE_CODE=1
    var profileImage: Uri = Uri.EMPTY
    lateinit var oldProfileImage:String

    lateinit var userPostAdapter:UserPostsAdapter

    private var flag=0

    override fun onRestart() {
        super.onRestart()
        setupGetUserPosts()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)

        setupGetUser()

        setupUserPostAdapter()
        setupGetUserPosts()

        setupChangePhoto()
        setupUpdateUser()
        confirmButton()

        back.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
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
                        val currentUser = response.body()!!

                        tv_first_name.text=currentUser.firstName
                        tv_last_name.text=currentUser.lastName

                        Log.d("USERNAME PRE: ", tv_userName.text.toString())
                        tv_userName.text=currentUser.username
                        Log.d("USERNAME POSLE: ", tv_userName.text.toString())

                        tv_email.text=currentUser.email

                        tv_name1.text=currentUser.firstName
                        tv_name2.text=currentUser.lastName

                        oldProfileImage = currentUser.profileImage
                        Log.d("oldProfileImage", oldProfileImage)
                        Picasso.get().load(UtilityFunctions.getFullImagePath(oldProfileImage)).into(imageViewReal)


                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    //TODO("Not yet implemented")
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
            et_username.setText(tv_userName.text)
            et_email.setText(tv_email.text)


            tv_first_name.isGone=true
            tv_last_name.isGone=true
            tv_userName.isGone=true
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
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

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



    private fun confirmButton()
    {
        ib_confirm.setOnClickListener()
        {

            val dialog= Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.modal_confirm_password)
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            dialog.btn_confirm_password.setOnClickListener(){

                var editUserRequest = EditUserRequest(
                    et_username.text.toString(),
                    dialog.et_modul_password.text.toString(),
                    et_first_name.text.toString(),
                    et_last_name.text.toString(),
                    et_email.text.toString()
                )


                val context: Context = this

                if(profileImage == Uri.EMPTY)
                    changeUserData(context, editUserRequest)
                else
                    changeUserImage(context)


                if(flag == 0) {
                    et_first_name.setText(tv_first_name.text)
                    et_last_name.setText(tv_last_name.text)
                    et_username.setText(tv_userName.text)
                    et_email.setText(tv_email.text)
                }

                profileImage = Uri.EMPTY

                dialog.dismiss()
            }
            dialog.show()

            ib_confirm.isInvisible=true

            tv_first_name.isGone=false
            tv_last_name.isGone=false
            tv_userName.isGone=false
            tv_email.isGone=false

            et_first_name.isGone=true
            et_last_name.isGone=true
            et_username.isGone=true
            et_email.isGone=true

            ib_update.isGone=false



        }

    }


    private fun changeUserData(context: Context, editUserRequest: EditUserRequest){
        apiClient.getUserService(this).editUserData(editUserRequest)
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful)
                    {
                        Log.d("",response.body()?.token.toString())
                        sessionManager.clearToken()
                        sessionManager.saveToken(response.body()?.token.toString())

                        Toast.makeText(context,"Credentials changed successfully!",Toast.LENGTH_LONG).show()

                        tv_first_name.text=et_first_name.text.toString()
                        tv_last_name.text=et_last_name.text.toString()
                        tv_userName.text=et_username.text.toString()
                        tv_email.text=et_email.text.toString()

                        tv_name1.text=et_first_name.text.toString()
                        tv_name2.text=et_last_name.text.toString()

                    }

                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Toast.makeText(context, "_BAD REQUEST MESSAGE HERE_", Toast.LENGTH_SHORT).show()

                    flag=0
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                }


            })
    }

    private fun changeUserImage(context: Context){
        apiClient.getUserService(this).editUserImage(
            UtilityFunctions.uriToMultipartPart(context, profileImage, "ProfileImage", "profile_image")
        )
            .enqueue(object : Callback<MessageResponse>{
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful)
                        Toast.makeText(context,"Profile image changed successfully!",Toast.LENGTH_LONG).show()

                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Toast.makeText(context, "_BAD REQUEST MESSAGE HERE_", Toast.LENGTH_SHORT).show()

                    flag=0
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                }


            })
    }




    private fun setupChangePhoto() {

        ib_change_photo.setOnClickListener(){

            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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

    private fun setupUserPostAdapter() {
        userPostAdapter = UserPostsAdapter(mutableListOf(),this, ::onPostDelete)
        gv_user_posts.adapter = userPostAdapter
    }

    private fun onPostDelete(){

    }


    private fun setupGetUserPosts() {

        var user=sessionManager.fetchUserData()

        apiClient.getPostService(this).getUserPosts(user!!.username)
            .enqueue(object : Callback<ArrayList<PostResponse>> {
                override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                    if(response.isSuccessful) {
                        userPostAdapter.setPostList(response.body()!!)
                    }

                }

                override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {

                }

            })


    }


}
