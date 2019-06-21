package com.muhasebe.tahsilatlar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.muhasebe.tahsilatlar.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Ahmet Oguz Er on 19.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            if(loginEmail.text.toString().isNotEmpty() && loginPassword.text.toString().isNotEmpty() ) {
                    firebaseAuth.signInWithEmailAndPassword(
                        loginEmail.text.toString(),
                        loginPassword.text.toString()
                    ).addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }

                }
        }

    }

}