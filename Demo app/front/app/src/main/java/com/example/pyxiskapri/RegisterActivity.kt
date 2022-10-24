package com.example.pyxiskapri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_password
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupGoToLoginButton();

        btn_goToRegister.setOnClickListener {
            val patern= Regex("^[^0-9][a-zA-Z0-9_]+\$")
            if(et_email.text.toString() != "" && et_password.text.toString() != "" && et_username.text.toString() != "")
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches())
                    if(et_email.length()>5 && et_password.length()>5)
                        if(et_password.text.contains(patern))
                            reg()
                        else
                            Toast.makeText(this, "Sifra ne moze zapoceti karakterom!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this, "Username i sifra moraju imati vise od 6 karaktera!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Unesite ispravan email!", Toast.LENGTH_SHORT).show()

            else
                Toast.makeText(this, "Morate popuniti sva polja!", Toast.LENGTH_SHORT).show()
        }

    }


    private fun setupGoToLoginButton(){
        btn_signInHere.setOnClickListener{
            val intent = Intent (this, SigninActivity::class.java);
            startActivity(intent);
        };
    }



    fun reg() {

        val okHttp = OkHttpClient().newBuilder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5231")
            .client(okHttp)

            .build()

        // Create Service
        val service = retrofit.create(ApiService::class.java)


        // Create JSON using JSONObject
        val jsonObject = JSONObject()

        jsonObject.put("email", et_email.text)
        jsonObject.put("username", et_username.text)
        jsonObject.put("password", et_password.text)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        //val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.postReg(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("","TEST -----------------------------------------------------------------------")
//                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                        JsonParser.parseString(
//                            response.body()
//                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                        )
//                    )

                //    Log.d("Pretty Printed JSON :", prettyJson)


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}