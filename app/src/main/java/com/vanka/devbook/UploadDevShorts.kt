package com.vanka.devbook

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.vanka.devbook.Model.DevPostModel
import com.vanka.devbook.Model.UseerModel
import java.util.*

class UploadDevShorts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var getImg:Uri?=null
        var image:String?=null
        var authorName:String?=null
        setContentView(R.layout.activity_upload_dev_shorts)
        findViewById<ProgressBar>(R.id.progressBar_ds).visibility = View.GONE
        var short_img = findViewById<ImageView>(R.id.shorts)
        var info_et = findViewById<EditText>(R.id.info_et)
        var uploadImage = findViewById<Button>(R.id.Upload_shorts)
        var getImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            var put = FirebaseStorage.getInstance().reference.child("TumbNails").child(
                UUID.randomUUID().toString())
            put.putFile(it!!).addOnSuccessListener {

                put.downloadUrl.addOnSuccessListener {vid->
                    getImg = vid
                    short_img.setImageURI(getImg)
                    getUserData()
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var data = snapshot.getValue(UseerModel::class.java)
                                image = data!!.imgLink
                                authorName = data.userName
                                var role = data.role
                                var auid = data.uid
                                UploadFrag(info_et,getImg,authorName,image,role,auid)

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })

                }
            }
        }
        uploadImage.setOnClickListener {
            findViewById<ProgressBar>(R.id.progressBar_ds).visibility = View.VISIBLE
            getImage.launch("image/*")
        }
    }

    private fun getUserData() {

    }

    private fun UploadFrag(
        infoEt: EditText?,
        img: Uri?,
        authorName: String?,
        image: String?,
        role: String?,
        auid: String?
    ) {

        var data = DevPostModel(infoEt!!.text.toString(),authorName,image,img.toString(),role,auid)
        FirebaseDatabase.getInstance().getReference("Post").push().setValue(data).addOnSuccessListener {
            Toast.makeText(this, "SuccessFull!!", Toast.LENGTH_SHORT).show()

                    findViewById<ProgressBar>(R.id.progressBar_ds).visibility = View.GONE
            startActivity(Intent(this,MainScreen::class.java))
                }
        }


    }

