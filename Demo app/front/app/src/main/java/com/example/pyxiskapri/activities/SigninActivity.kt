package com.example.pyxiskapri.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.ApiService
import com.example.pyxiskapri.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.et_password
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


class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setupGoToRegisterButton();


        btn_signIn.setOnClickListener(){
            //if(et_emailOrUsername.text.toString() != "" && et_password.text.toString() != "")
            val patern= Regex("^[^0-9][a-zA-Z0-9_]+\$")
            if(et_emailOrUsername.text.toString() != "" && et_password.text.toString() != "")
                if(et_emailOrUsername.length()>5 && et_password.length()>5)
                    if(et_password.text.contains(patern))
                    {
                        var login=login()
                        if(login)
                        {
                            val intent = Intent (this, LoggedActivity::class.java);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(this, "Neupesno prijavljivanje!", Toast.LENGTH_SHORT).show()
                    }

                    else
                            Toast.makeText(this, "Sifra ne moze zapoceti karakterom!", Toast.LENGTH_SHORT).show()
                else
                        Toast.makeText(this, "Username i sifra moraju imati vise od 6 karaktera!", Toast.LENGTH_SHORT).show()
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

    @SuppressLint("RestrictedApi")
    fun login():Boolean {

        var bool:Boolean=false

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

        jsonObject.put("username", et_emailOrUsername.text)
        jsonObject.put("password", et_password.text)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response

            Log.d("", "Pre slanja")
            val response = service.postLogin(requestBody)

            Log.d("", "Posle slanja")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    bool=true
                    Log.d("", bool.toString())

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    //val company = gson.fromJson(response.body().toString(), Data::class.java)
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("", prettyJson)


                    val prefs= getSharedPreferences("MySharedPrefs",Context.MODE_PRIVATE)

                    val edit= prefs.edit()
                    edit.putString("token",prettyJson)
                    edit.commit()




                } else {

                    Log.d("","pogresna lozinka")
                    bool=false
                }
            }
        }
        Log.d("","POSLEDNJI RETURN")
        Log.d("",bool.toString())
        return bool
    }


}