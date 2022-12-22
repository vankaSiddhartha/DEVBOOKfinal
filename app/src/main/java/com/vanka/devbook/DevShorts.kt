package com.vanka.devbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.devbook.Adapter.DevShortsAdapter
import com.vanka.devbook.Adapter.HomeAdapter
import com.vanka.devbook.Model.DevPostModel


class DevShorts : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_dev_shorts, container, false)
        var ds_rv = view.findViewById<RecyclerView>(R.id.ds_rv)
        ds_rv.layoutManager = LinearLayoutManager(requireContext())
        retriveData()
        return view
    }

    private fun retriveData() {
        var list = ArrayList<DevPostModel>()
        FirebaseDatabase.getInstance().getReference("Post").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children){
                    var getdata = data.getValue(DevPostModel::class.java)
                    list.add(getdata!!)
                }
                try{
                    view!!.findViewById<RecyclerView>(R.id.ds_rv).adapter = DevShortsAdapter(requireContext(),list)
                }catch (e:Exception){

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}