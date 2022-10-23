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
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupGoToLoginButton();

        btn_goToRegister.setOnClickListener {
            if(et_email.text != null && et_password != null && et_username != null)
                reg()
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
        val retrofit = Retrofit.Builder()
            .baseUrl("127:0:0:1")
            .build()

        // Create Service
        val service = retrofit.create(ApiService::class.java)

        val regdto = RegDTO(et_email.text.toString(),et_username.text.toString(),et_password.text.toString())

        // Create JSON using JSONObject
    /*    val jsonObject = JSONObject()

        jsonObject.put("email", et_email.text)
        jsonObject.put("username", et_username.text)
        jsonObject.put("password", et_password.text)*//*

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
*/

        //val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.postReg(regdto)

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

                //    Log.d("Pretty Printed JSON :", prettyJson)


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}