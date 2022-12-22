package com.vanka.devbook.Adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanka.devbook.Model.DevPostModel
import com.vanka.devbook.R

class DevShortsAdapter(var context: Context,var list: ArrayList<DevPostModel>) : RecyclerView.Adapter<DevShortsAdapter.ViewHolder>(){
    class ViewHolder(var itemview: View):RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.shorts_list_view,parent,false)
        return DevShortsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.itemview.findViewById<TextView>(R.id.shorts_text).setMovementMethod(
                ScrollingMovementMethod()
            )
            holder.itemview.findViewById<TextView>(R.id.shorts_text).text = list[position].text
            Glide.with(context).load(list[position].img).into(holder.itemview.findViewById(R.id.shorts_img))
            Glide.with(context).load(list[position].authourImg).into(holder.itemview.findViewById(R.id.Authourprofile))
            holder.itemView.findViewById<TextView>(R.id.Authour_name).text = list[position].authourName
        holder.itemView.findViewById<TextView>(R.id.authour_name).text = list[position].role


    }

    override fun getItemCount(): Int {
       return list.size
    }
}