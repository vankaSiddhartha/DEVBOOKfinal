package com.vanka.devbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.devbook.Adapter.FallowAdapter
import com.vanka.devbook.Model.UseerModel


class Following : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view =inflater.inflate(R.layout.fragment_following, container, false)
        var rv = view.findViewById<RecyclerView>(R.id.f_rv)
        rv.layoutManager = LinearLayoutManager(requireContext())
        showData()
        return view
    }

    private fun showData() {
        var list = ArrayList<UseerModel>()
      FirebaseDatabase.getInstance().getReference("Fallow").child(FirebaseAuth.getInstance().currentUser!!.uid)
          .addValueEventListener(object : ValueEventListener{
              override fun onDataChange(snapshot: DataSnapshot) {
                  for (data in snapshot.children){
                      var getData = data.getValue(UseerModel::class.java)
                      list.add(getData!!)

                  }
                  try {
                      view!!.findViewById<RecyclerView>(R.id.f_rv).adapter = FallowAdapter(requireContext(),list)
                  }catch (e:Exception){

                  }


              }

              override fun onCancelled(error: DatabaseError) {

              }

          })
    }

}