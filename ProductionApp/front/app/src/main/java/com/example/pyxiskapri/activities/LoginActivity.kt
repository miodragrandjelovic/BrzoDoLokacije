package com.example.pyxiskapri.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.response.LoginRequest
import com.example.pyxiskapri.dtos.response.LoginResponse
import com.example.pyxiskapri.utility.ActivityControl
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.Constants
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = SessionManager(this)

        ActivityControl.handleUserSignedIn(this,this, sessionManager, savedInstanceState)

        apiClient = ApiClient()

        setupGoToRegisterButton();
        setupSignInButton();
    }

    override fun onRestart() {
        super.onRestart();
        ActivityControl.handleUserSignedIn(this,this, sessionManager, null)
        resetInputs()
    }

    private fun resetInputs(){
        et_usernameOrEmail.setText("")
        et_password.setText("")
    }

    private fun setupGoToRegisterButton(){
        btn_registerHere.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java);
            startActivity(intent);
            finish()
        };
    }


    private fun setupSignInButton(){
        btn_login.setOnClickListener {
            if(et_usernameOrEmail.text.toString() != "" && et_password.text.toString() != "")
                if(et_usernameOrEmail.length()>5 && et_password.length()>5)
                    login()
                else
                    Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "You need to fill in all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    fun login() {
        val loginRequest = LoginRequest(
            usernameOrEmail = et_usernameOrEmail.text.toString(),
            password = et_password.text.toString()
        )

        val context: Context = this
        apiClient.getUserService(context).login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful) {
                        sessionManager.saveToken(response.body()?.token.toString())
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        finishAffinity()
                        startActivity(intent)
                    }

                    if(response.code() == Constants.CODE_NOT_FOUND)
                        Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show()
                    if(response.code() == Constants.CODE_BAD_REQUEST)
                        Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            })
    }
}