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
import com.vanka.devbook.Model.UseerModel
import com.vanka.devbook.Model.VideoModel
import java.util.*

class UploadVideo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video)
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE

        var getvid: Uri?=null
        var getImg: Uri?=null
        var name:String?=null
        var Auid:String?=null
        var profile:String?=null
        var sde:String?=null
        var titile = findViewById<EditText>(R.id.up_title)
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var data = snapshot.getValue(UseerModel::class.java)
                    name = data!!.userName
                    profile = data!!.imgLink
                    Auid = data!!.uid
                    sde = data.role

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        var getImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            var put = FirebaseStorage.getInstance().reference.child("TumbNails").child(
                UUID.randomUUID().toString())
            put.putFile(it!!).addOnSuccessListener {

                put.downloadUrl.addOnSuccessListener {vid->
                    getImg = vid
                    findViewById<ImageView>(R.id.up_tumbnail).setImageURI(getImg)
                    UploadFrag(titile,getvid,getImg,name,profile,Auid,sde)
                }
            }
        }

        var getVideo = registerForActivityResult(ActivityResultContracts.GetContent()){
            var put = FirebaseStorage.getInstance().reference.child("Videos").child(
                UUID.randomUUID().toString())
            put.putFile(it!!).addOnSuccessListener {

                put.downloadUrl.addOnSuccessListener {vid->
                    getvid = vid
                    getImage.launch("image/*")
                }
            }
        }





        var up_video =  findViewById<Button>(R.id.up_video).setOnClickListener {

            getVideo.launch("video/*")
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        }

    }


    private fun UploadFrag(
        titile: EditText?,
        getvid: Uri?,
        img: Uri?,
        name: String?,
        profile: String?,
        Auid: String?,
        sde: String?
    ) {
        var data = VideoModel(name,titile!!.text.toString(),img.toString(),profile,getvid.toString(),Auid,sde)
       FirebaseDatabase.getInstance().getReference("VideosList").push().setValue(data).addOnSuccessListener {
           try {
               findViewById<ProgressBar>(R.id.progressBar_ds).visibility = View.GONE

           }catch (e:Exception){

           }
           startActivity(Intent(this,MainScreen::class.java))
       }
    }


}