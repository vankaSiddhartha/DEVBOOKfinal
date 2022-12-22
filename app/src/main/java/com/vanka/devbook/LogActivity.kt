package com.vanka.devbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogActivity : AppCompatActivity() {
    var i=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        var email = findViewById<EditText>(R.id.emailL)
        var pass = findViewById<EditText>(R.id.passL)
        var passR = findViewById<EditText>(R.id.passRL)
        var show_btn = findViewById<ImageView>(R.id.passShowbtnL)
        findViewById<Button>(R.id.login_btn).setOnClickListener {
            Auth(email, pass, passR)
        }


        show_btn.setOnClickListener {
            i++
            if (i % 2 != 0) {
                passR.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show_btn.setImageResource(R.drawable.view)
            } else {
                passR.transformationMethod = PasswordTransformationMethod.getInstance()
                show_btn.setImageResource(R.drawable.hidden)
            }

        }

    }

    private fun Auth(email: EditText?, pass: EditText?, passR: EditText?) {
        var get_email = email!!.text.toString().trim()
        var get_pass = pass!!.text.toString().trim()
        var get_pass_re = passR!!.text.toString().trim()
        if (get_email.isNotEmpty() && get_pass.isNotEmpty() && get_pass_re.isNotEmpty()) {
            if (get_pass.equals(get_pass_re)) {
                if (get_pass_re.length >= 8) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(get_email, get_pass_re)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Welcome to club bro!!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainScreen::class.java))
                        }.addOnFailureListener {
                            if (it.toString().contains("FirebaseAuthInvalidCredentialsException"))
                                Toast.makeText(this, "Weak password!!", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(
                                    this,
                                    "Change email some thing went wrong!!!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                } else {
                    Toast.makeText(this, "Password should be long!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Password is not matching!!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Empty filled not allowed!!", Toast.LENGTH_SHORT).show()
        }
    }
}
