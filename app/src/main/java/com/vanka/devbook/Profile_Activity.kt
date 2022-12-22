package com.vanka.devbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.vanka.devbook.Adapter.DevShortsAdapter
import com.vanka.devbook.Adapter.HomeAdapter
import com.vanka.devbook.Model.DevPostModel
import com.vanka.devbook.Model.VideoModel

class Profile_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getVideos()
        var name = findViewById<TextView>(R.id.user_name)
        var role = findViewById<TextView>(R.id.user_role)
        var img = findViewById<ImageView>(R.id.user_img)
        var getname = intent.getStringExtra("name")
        var getrole = intent.getStringExtra("role")
        var getImg = intent.getStringExtra("img")
        name.text = intent.getStringExtra("name")
        role.text = intent.getStringExtra("role")
        var link = intent.getStringExtra("Auid")
        Glide.with(this).load(intent.getStringExtra("img")).into(img)
    findViewById<ImageView>(R.id.message).setOnClickListener {
        var intent = Intent(this,MessageActivity::class.java)
        intent.putExtra("auid",link)
        intent.putExtra("rname",getname)
        intent.putExtra("rrole",getrole)
        intent.putExtra("rimg",getImg)
        startActivity(intent)
    }





        findViewById<RecyclerView>(R.id.user_rv).layoutManager = LinearLayoutManager(this)
       findViewById<ImageView>(R.id.user_video).setOnClickListener {
           getVideos()
       }
        findViewById<ImageView>(R.id.user_shorts).setOnClickListener {
            getShorts()
        }


    }

    private fun startOther() {

    }

    private fun getShorts() {
        var list = ArrayList<DevPostModel>()
        //Toast.makeText(this@Profile_Activity, "${intent.getStringExtra("Auid")}", Toast.LENGTH_SHORT).show()
        FirebaseDatabase.getInstance().getReference("Post").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                   var data = snap.getValue(DevPostModel::class.java)
                    if (data!!.uid!!.equals(intent.getStringExtra("Auid")))

                    list.add(data)
                }
                try{
                    findViewById<RecyclerView>(R.id.user_rv).adapter  = DevShortsAdapter(this@Profile_Activity,list)
                }catch (e:Exception){

                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun getVideos() {
        FirebaseDatabase.getInstance().getReference("VideosList").addValueEventListener(object :
            ValueEventListener {
            var list = ArrayList<VideoModel>()
            override fun onDataChange(snapshot: DataSnapshot) {

                for (snap in snapshot.children) {
                    var data = snap.getValue(VideoModel::class.java)

                    if(data!!.authourUid!!.equals(intent.getStringExtra("Auid")))
                    list.add(data!!)


                }
                findViewById<RecyclerView>(R.id.user_rv).adapter = HomeAdapter(this@Profile_Activity,list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}