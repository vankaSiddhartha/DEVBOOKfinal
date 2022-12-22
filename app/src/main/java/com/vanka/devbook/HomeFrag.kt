package com.vanka.devbook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import com.vanka.devbook.Adapter.HomeAdapter
import com.vanka.devbook.Model.UseerModel
import com.vanka.devbook.Model.VideoModel


class HomeFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            var name:String?=null
        var role:String?=null
        var uid:String?=null
        var gimg:String?= null
           var view = inflater.inflate(R.layout.fragment_home, container, false)
         var home_rv = view.findViewById<RecyclerView>(R.id.home_rv)

        home_rv.layoutManager = LinearLayoutManager(requireContext())
        retriveData()
       var text = view.findViewById<EditText>(R.id.search)
        view.findViewById<ImageView>(R.id.search_btn).setOnClickListener {
            retriveData1(text)
        }
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var img = snapshot.getValue(UseerModel::class.java)
                        Glide.with(requireContext()).load(img!!.imgLink)
                            .into(view!!.findViewById<ImageView>(R.id.home_profile))
                        name = img.userName
                        role=img.role
                        uid = img.uid
                        gimg = img.imgLink
                    }catch (e:Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retriveData()
                text.setText("")
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        view.findViewById<ImageView>(R.id.home_profile).setOnClickListener {
            var intent = Intent(requireContext(),Profile_Activity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("img",gimg)
            intent.putExtra("Auid",uid)
            intent.putExtra("role",role)
            startActivity(intent)
        }
        return view
    }

    private fun retriveData1(text: EditText?) {
        FirebaseDatabase.getInstance().getReference("VideosList").addValueEventListener(object :
            ValueEventListener{
            var list = ArrayList<VideoModel>()
            override fun onDataChange(snapshot: DataSnapshot) {

                for(snap in snapshot.children){
                    var data = snap.getValue(VideoModel::class.java)

                     if (data!!.title!!.contains(text!!.text.toString(), ignoreCase = true)) {

                         list.add(data!!)
                     }
                }
                try {
                    view!!.findViewById<RecyclerView>(R.id.home_rv).adapter = HomeAdapter(requireContext(),list);
                }catch (e:Exception){}


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun retriveData() {

        FirebaseDatabase.getInstance().getReference("VideosList").addValueEventListener(object :
            ValueEventListener{
            var list = ArrayList<VideoModel>()
            override fun onDataChange(snapshot: DataSnapshot) {

                for(snap in snapshot.children){
                    var data = snap.getValue(VideoModel::class.java)


                    list.add(data!!)
                    try{
                        view!!.findViewById<ProgressBar>(R.id.progressBar_home).visibility = View.GONE
                    }catch (e:Exception){

                    }

                }
                try {
                    view!!.findViewById<RecyclerView>(R.id.home_rv).adapter = HomeAdapter(requireContext(),list);
                }catch (e:Exception){}


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }



}