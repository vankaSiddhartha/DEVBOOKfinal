package com.vanka.devbook

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.vanka.devbook.Model.UseerModel

class CreateAccount2 : AppCompatActivity() {
    private fun UploadData(img: Uri?, name: EditText?, role: EditText?, experence: EditText?, id: String) {
        var data = UseerModel(name!!.text.toString(),id.toString(),role!!.text.toString(),experence!!.text.toString(),img.toString())
        FirebaseDatabase.getInstance().getReference("Users").child(id).setValue(data).addOnSuccessListener {
            findViewById<ProgressBar>(R.id.progressBar_c2).visibility = View.VISIBLE
            startActivity(Intent(this,MainScreen::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            findViewById<ProgressBar>(R.id.progressBar_c2).visibility = View.VISIBLE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        var k:Uri?=null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account2)
        findViewById<ProgressBar>(R.id.progressBar_c2).visibility = View.GONE
        var name = findViewById<EditText>(R.id.f_name)
        var role = findViewById<EditText>(R.id.role)
        var experence = findViewById<EditText>(R.id.ex)
        var id = FirebaseAuth.getInstance().currentUser!!.uid
        val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
            findViewById<ImageView>(R.id.profile).setImageURI(it)

            var put = FirebaseStorage.getInstance().reference.child("Files").child(FirebaseAuth.getInstance().currentUser!!.uid)
            put.putFile(it!!).addOnSuccessListener {
                Toast.makeText(this, "OM", Toast.LENGTH_SHORT).show()
                put.downloadUrl.addOnSuccessListener {img->
                    k = img
                }
            }
        }
        findViewById<ImageView>(R.id.profile).setOnClickListener {


            contract.launch("image/*")

        }
        findViewById<Button>(R.id.btn_signup).setOnClickListener {
            findViewById<ProgressBar>(R.id.progressBar_c2).visibility = View.VISIBLE
            UploadData(k, name, role, experence, id)
        }
    }
}