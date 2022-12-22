package com.vanka.devbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.vanka.devbook.Adapter.MessageAdapter
import com.vanka.devbook.Model.MessageModel
import com.vanka.devbook.Model.UseerModel

class MessageActivity : AppCompatActivity() {
    var q=0
    var   list:ArrayList<MessageModel>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
       var ruid = intent.getStringExtra("auid");
        var rrole = intent.getStringExtra("rrole")
        var rname = intent.getStringExtra("rname")
        var rimg = intent.getStringExtra("rimg")
        var suid = FirebaseAuth.getInstance().currentUser!!.uid
        var senderRoom = ruid+suid
        var reseverRoom = suid+ruid
        var send_mess = findViewById<EditText>(R.id.send_message)
        var send_message_btn = findViewById<ImageView>(R.id.send_btn).setOnClickListener{
            sendMessage(senderRoom,reseverRoom,send_mess,ruid)

        }
        try{
            GETT(suid+ruid)
        }catch (e:Exception){

        }
        findViewById<RecyclerView>(R.id.message_rv).layoutManager = LinearLayoutManager(this)
        addChatFallow(ruid,rrole,rname,rimg)


    }

    private fun addChatFallow(ruid: String?, rrole: String?, rname: String?, rimg: String?) {
        var data = UseerModel(rname,ruid,rrole,"",rimg)
        FirebaseDatabase.getInstance().getReference("Fallow").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(ruid!!).setValue(data).addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data = snapshot.getValue(UseerModel::class.java)
                        FirebaseDatabase.getInstance().getReference("Fallow").child(ruid).child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(data)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            }
    }

    private fun sendMessage(
        senderRoom: String,
        reseverRoom: String,
        sendMess: EditText?,
        ruid: String?
    ) {
    var data = MessageModel(FirebaseAuth.getInstance().currentUser!!.uid,sendMess!!.text.toString())
        var data2 = MessageModel(ruid, sendMess.text.toString())
        FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).push().setValue(data).addOnSuccessListener {
            FirebaseDatabase.getInstance().getReference("Chats").child(reseverRoom).push().setValue(data).addOnSuccessListener {
                findViewById<EditText>(R.id.send_message).setText("")
            }
        }
    }
    private fun GETT(ROOMID: String) {
        FirebaseDatabase.getInstance().getReference("Chats").child(ROOMID)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                  list = ArrayList<MessageModel>()

                    for (data in snapshot.children){
                        if(snapshot.exists()){
                            q++;
                           val getInfo = data.getValue(MessageModel::class.java)


                           list!!.add(getInfo!!)



                        }


                    }

                    findViewById<RecyclerView>(R.id.message_rv).adapter = MessageAdapter(this@MessageActivity,list!!)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}