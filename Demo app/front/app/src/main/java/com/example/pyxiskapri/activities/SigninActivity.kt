package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.ApiService
import com.example.pyxiskapri.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setupGoToRegisterButton();

        setupSignInButton();
    }

    private fun setupGoToRegisterButton(){
        btn_registerHere.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java);
            startActivity(intent);
        };
    }


    private fun setupSignInButton(){
        btn_signIn.setOnClickListener(){
            //if(et_emailOrUsername.text.toString() != "" && et_password.text.toString() != "")
            val patern= Regex("^[^0-9][a-zA-Z0-9_]+\$")
            if(et_emailOrUsername.text.toString() != "" && et_password.text.toString() != "")
                if(et_emailOrUsername.length()>5 && et_password.length()>5)
                    if(et_password.text.contains(patern))
                        login()

                    /*
                    {
                        var login=login(this)
                        if(login)
                        {
                            val intent = Intent (this, HomeActivity::class.java);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(this, "Neupesno prijavljivanje!", Toast.LENGTH_SHORT).show()

                    }
                     */

                    else
                        Toast.makeText(this, "Sifra ne moze zapoceti karakterom!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Username i sifra moraju imati vise od 6 karaktera!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Morate popuniti sva polja!", Toast.LENGTH_SHORT).show()


        }
    }

    fun login() {

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

        jsonObject.put("usernameOrEmail", et_emailOrUsername.text)
        jsonObject.put("password", et_password.text)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        var context: Context = this
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response

            Log.d("", "Pre slanja")
            val response = service.postLogin(requestBody)

            Log.d("", "Posle slanja")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    //val company = gson.fromJson(response.body().toString(), Data::class.java)

                    // NAPRAVITI KLASU KOJA CE SADRZATI PODATKE DOBIJENE SA SERVERA ( DTO )
                    val obj: JsonObject = JsonParser.parseString(response.body()?.string()) as JsonObject


                    Log.d("",obj.toString())
                    Log.d("","RAZMAK")
                    Log.d("",obj.get("token").asString)


                    //DEKODIRANJE JWTA -----------------------------------------------------------

                    var split= obj.get("token").toString().split(".")

                    var header= String(Base64.decode(split[0],Base64.URL_SAFE))
                    var payload= String(Base64.decode(split[1],Base64.URL_SAFE))
                    var signature= String(Base64.decode(split[2],Base64.URL_SAFE))
                    Log.d("",header)
                    Log.d("",payload)
                    Log.d("",signature)
                    // ---------------------------------------------------------------------------

                    val prefs = getSharedPreferences("MySharedPrefs",Context.MODE_PRIVATE)

                    prefs.edit()
                        .putString("token", obj.get("token").asString)
                        .putString("username", obj.get("username").asString)
                        .putString("email", obj.get("email").asString)
                        .commit()

                    val intent = Intent (context, HomeActivity::class.java);
                    startActivity(intent);

                } else {

                    Toast.makeText(context, "Neuspesno prijavljivanje!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("","POSLEDNJI RETURN")
    }


}