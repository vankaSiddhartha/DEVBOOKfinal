package com.vanka.devbook.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanka.devbook.MessageActivity
import com.vanka.devbook.Model.UseerModel
import com.vanka.devbook.R

class FallowAdapter(var context: Context,var list:ArrayList<UseerModel>):RecyclerView.Adapter<FallowAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.fallow_list,parent,false)
        return FallowAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemView.findViewById<TextView>(R.id.f_name).text = list[position].userName
        holder.itemView.findViewById<TextView>(R.id.f_role).text = list[position].role
        Glide.with(context).load(list[position].imgLink).into(holder.itemView.findViewById(R.id.f_profile ))
        holder.itemView.setOnClickListener {
            var intent = Intent(context,MessageActivity::class.java)
            intent.putExtra("auid",list[position].uid)
            intent.putExtra("rname",list[position].userName)
            intent.putExtra("rrole",list[position].role)
            intent.putExtra("rimg",list[position].imgLink)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
     return list.size
    }
}