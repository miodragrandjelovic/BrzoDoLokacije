package com.example.pyxiskapri.activities.UserProfile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.FollowListActivity
import com.example.pyxiskapri.dtos.request.ChangePasswordRequest
import com.example.pyxiskapri.dtos.request.EditUserRequest
import com.example.pyxiskapri.dtos.response.GetUserResponse
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.models.FollowList
import com.example.pyxiskapri.utility.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_change_credentials.*
import kotlinx.android.synthetic.main.modal_change_pass.*
import kotlinx.android.synthetic.main.modal_confirm_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ChangeCredentialsActivity : AppCompatActivity() {

    lateinit var apiClient: ApiClient
    lateinit var sessionManager: SessionManager

    private val PICK_IMAGE_CODE=1
    var profileImage: Uri = Uri.EMPTY

    override fun onRestart() {
        super.onRestart()
        //setupGetUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_credentials)

        apiClient=ApiClient()
        sessionManager= SessionManager(this)


        var changeCredentialsInformation = ActivityTransferStorage.changeCredentialsInformation
        Picasso.get().load(UtilityFunctions.getFullImagePath(changeCredentialsInformation.coverImage)).into(coverImage_c)

        post_number_c.text = changeCredentialsInformation.postsNumber

        setupGetUser()
        tv_change_pass.setOnClickListener(){
            changepass()
        }
        setupChangePhoto()
        saveChanges()

        setupGetFollowers()
        setupGetFollowing()

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

                        et_email.setText(response.body()!!.email)

                        tv_name1_c.text=response.body()!!.firstName
                        tv_name2_c.text=response.body()!!.lastName

                        followers_count_c.text = response.body()!!.followingCount.toString()
                        following_count_c.text = response.body()!!.followersCount.toString()


                        val picture=response.body()!!.profileImage
                        if(picture!=null)
                            Picasso.get().load(UtilityFunctions.getFullImagePath(picture)).into(iv_profileImageView)


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
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            dialog.btn_confirm_password.setOnClickListener() {



                var editUserRequest = EditUserRequest(

                    firstName = this.et_first_name_n.text.toString(),
                    lastName = this.et_last_name_n.text.toString(),
                    username = this.et_username_n.text.toString(),
                    email = this.et_email.text.toString(),
                    password = dialog.et_modul_password.text.toString(),
                )


                val context: Context = this

                changeUserData(context, editUserRequest)

                if(profileImage != Uri.EMPTY)
                    changeUserImage(context)

                profileImage = Uri.EMPTY

                dialog.dismiss()

            }
            dialog.show()
        }
    }

    private fun changeUserData(context: Context, editUserRequest: EditUserRequest){

        apiClient.getUserService(context).editUserData(editUserRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
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


    }

    private fun changeUserImage(context: Context){
        apiClient.getUserService(this).editUserImage(
            UtilityFunctions.uriToMultipartPart(context, profileImage, "ProfileImage", "profile_image")
        )
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful) {
                        var token = response.body()?.token!!
                        SessionManager(context).clearToken()
                        SessionManager(context).saveToken(token)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong, try again.",Toast.LENGTH_LONG).show()
                }


            })
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

    private fun setupGetFollowers() {

        ll_followers_c.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = false
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }

    private fun setupGetFollowing() {

        ll_following_c.setOnClickListener(){

            var followList = FollowList(
                username = SessionManager(this).fetchUserData()?.username!!,
                type = true
            )

            val intent = Intent(this, FollowListActivity::class.java);
            ActivityTransferStorage.followList = followList
            startActivity(intent);

        }

    }


    private fun setupChangePhoto() {


        tv_change_picture.setOnClickListener(){

            val intent: Intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != PICK_IMAGE_CODE || resultCode != Activity.RESULT_OK || data == null)
            return

//        if (data!!.clipData != null) {
//            profileImage = data.clipData!!.getItemAt(0).uri
//        }
//        else {
//
//        }

        val imageUri = data.data
        profileImage = imageUri!!

        iv_profileImageView.setImageURI(profileImage)

    }

}