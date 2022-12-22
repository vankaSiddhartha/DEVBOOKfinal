package com.vanka.devbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.devbook.Adapter.HomeAdapter
import com.vanka.devbook.Model.UseerModel
import com.vanka.devbook.Model.VideoModel


class LibraryFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_library, container, false)
        var home_rv = view.findViewById<RecyclerView>(R.id.library_rv)
        home_rv.layoutManager = LinearLayoutManager(requireContext())
        retriveData()
        var text = view.findViewById<EditText>(R.id.search_lib)
        view.findViewById<ImageView>(R.id.search_btnlib).setOnClickListener {
            retriveData1(text)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retriveData()
                text.setText("")
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return view
    }
    private fun retriveData1(text: EditText?) {
        FirebaseDatabase.getInstance().getReference("Lib").child(FirebaseAuth.getInstance().currentUser!!.uid).addValueEventListener(object :
            ValueEventListener{
            var list = ArrayList<VideoModel>()
            override fun onDataChange(snapshot: DataSnapshot) {

                for (snap in snapshot.children) {
                    var data = snap.getValue(VideoModel::class.java)

                    if (data!!.title!!.contains(text!!.text.toString(), ignoreCase = true)) {

                        list.add(data!!)
                    }
                }

                view!!.findViewById<RecyclerView>(R.id.library_rv).adapter =
                    HomeAdapter(requireContext(), list);
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun retriveData() {
        FirebaseDatabase.getInstance().getReference("Lib").child(FirebaseAuth.getInstance().currentUser
        !!.uid).addValueEventListener(object :
            ValueEventListener{
            var list = ArrayList<VideoModel>()
            override fun onDataChange(snapshot: DataSnapshot) {

                for(snap in snapshot.children){
                    var data = snap.getValue(VideoModel::class.java)


                    list.add(data!!)
                }
                      try {
                          view!!.findViewById<RecyclerView>(R.id.library_rv).adapter = HomeAdapter(requireContext(),list);
                      }catch (e:Exception){

                      }




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }



}


