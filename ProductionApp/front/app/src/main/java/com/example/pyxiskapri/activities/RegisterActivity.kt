package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pyxiskapri.services.UserService
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.RegisterRequest
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        apiClient = ApiClient()

        setupGoToLoginButton()
        setupRegisterButton()
    }


    private fun setupGoToLoginButton(){
        btn_signInHere.setOnClickListener{
            val intent = Intent (this, LoginActivity::class.java);
            startActivity(intent);
        };
    }

    private fun setupRegisterButton(){
        btn_goToRegister.setOnClickListener {
            val patern= Regex("^[^0-9][a-zA-Z0-9_]+\$")
            if( et_firstName.text.toString().trim() != "" && et_lastName.text.toString().trim() != "" && et_username.text.toString().trim() != "" && et_email.text.toString().trim() != "" && et_password.text.toString().trim() != "")
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches())
                    if(et_username.length()>5)
                        if(et_password.length()>5)
                            if(et_password.text.contains(patern))
                                register()
                            else
                                Toast.makeText(this, "Password can't begin with a digit!", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Password must contain at least 6 characters!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this, "Username must contain at least 6 characters!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Incorrect e-mail format!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "All fields must be filled!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun register() {

        val registerRequest: RegisterRequest = RegisterRequest(
            firstName = et_firstName.text.toString(),
            lastName = et_lastName.text.toString(),
            username = et_username.text.toString(),
            email = et_email.text.toString(),
            password = et_password.text.toString()
        )

        val context: Context = this
        apiClient.getUserService(context).register(registerRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful) {
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }

                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Toast.makeText(context, "Username is taken!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            })
    }

}

