package com.example.pyxiskapri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setupGoToRegisterButton();

        btn_signIn.setOnClickListener(){
            if(et_emailOrUsername.text != null && et_password != null)
                login()
            else
                Toast.makeText(this, "Morate popuniti sva polja!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupGoToRegisterButton(){
        btn_registerHere.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java);
            startActivity(intent);
        };
    }

    fun login() {
        val retrofit = Retrofit.Builder()
            .baseUrl("127:0:0:1")
            .build()

        // Create Service
        val service = retrofit.create(ApiService::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()

        jsonObject.put("username", et_emailOrUsername.text)
        jsonObject.put("password", et_password.text)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.postLogin(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

//                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                        JsonParser.parseString(
//                            response.body()
//                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                        )
//                    )

                   // Log.d("Pretty Printed JSON :", prettyJson)


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }


}