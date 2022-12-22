package com.vanka.devbook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.vanka.devbook.Model.MessageModel
import com.vanka.devbook.R

class MessageAdapter(var context:Context,var list:ArrayList<MessageModel>):RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var reciveR = 0;
    var sendL = 1;
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var mess = itemView.findViewById<TextView>(R.id.Text )
    }

    override fun getItemViewType(position: Int): Int {
        if(list[position].SID!=FirebaseAuth.getInstance().currentUser!!.uid)
            return reciveR


        else
            return  sendL
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         if(viewType==reciveR){
             return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recive_item,parent,false))
         }else{
             return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false))
         }
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.mess.text= list[position].message!!
   
     }

     override fun getItemCount(): Int {
          return list.size
     }
 }