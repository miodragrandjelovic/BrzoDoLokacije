package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.activity_main.*

class  MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGoToRegisterButton();
        setupGoToSignInButton();
        setupGoToAddPostButton()
    }


    private fun setupGoToRegisterButton(){
        btn_goToRegister.setOnClickListener{
            val intent = Intent (this, RegisterActivity::class.java);
            startActivity(intent);
        };
    }

    private fun setupGoToSignInButton(){
        btn_goToSignIn.setOnClickListener{
            val intent = Intent (this, LoginActivity::class.java);
            startActivity(intent);
        };

    }
    private fun setupGoToAddPostButton() {
        btn_goToAddPost.setOnClickListener {
            val intent = Intent(this, NewPostActivity::class.java);
            startActivity(intent);
        };
    }

}