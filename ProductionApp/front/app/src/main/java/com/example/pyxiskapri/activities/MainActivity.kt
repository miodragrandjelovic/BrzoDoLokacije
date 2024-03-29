package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pyxiskapri.R
import com.example.pyxiskapri.utility.ActivityControl
import com.example.pyxiskapri.utility.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class  MainActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        ActivityControl.handleUserSignedIn(this, this, sessionManager, savedInstanceState)

        setupGoToRegisterButton()
        setupGoToSignInButton()

    }


    override fun onRestart() {
        super.onRestart();
        ActivityControl.handleUserSignedIn(this, this, sessionManager, null)
    }

    private fun setupGoToRegisterButton(){
        btn_goToRegister.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java);
            startActivity(intent);
            finish()
        };
    }

    private fun setupGoToSignInButton(){
        btn_goToSignIn.setOnClickListener{
            val intent = Intent (this, LoginActivity::class.java);
            startActivity(intent);
            finish()
        };
    }

}