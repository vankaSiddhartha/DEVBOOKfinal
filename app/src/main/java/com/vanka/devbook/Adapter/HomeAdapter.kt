package com.vanka.devbook.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import com.vanka.devbook.Model.VideoModel
import com.vanka.devbook.Profile_Activity
import com.vanka.devbook.R
import com.vanka.devbook.videoPlayAct

class HomeAdapter(var context: Context,var list: ArrayList<VideoModel>): RecyclerView.Adapter<HomeAdapter.viewHolder>() {
    class viewHolder(itemview: View): RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.home_list,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.Title).text = list[position].title
        holder.itemView.findViewById<TextView>(R.id.title2).text = list[position].author
        Glide.with(context).load(list[position].thumbnail).into(holder.itemView.findViewById(R.id.imageView2))
        Glide.with(context).load(list[position].authorImg).into(holder.itemView.findViewById(R.id.imageView))
        holder.itemView.findViewById<ImageView>(R.id.imageView).setOnClickListener {

        }
        holder.itemView.findViewById<ImageView>(R.id.addlib).setOnClickListener {
           var data = VideoModel(list[position].author,list[position].title,list[position].thumbnail,list[position].authorImg,list[position].videoLink)
            FirebaseDatabase.getInstance().getReference("Lib").child(FirebaseAuth.getInstance().currentUser!!.uid).push().setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(context, "Add to library", Toast.LENGTH_SHORT).show()
                }
        }
        holder.itemView.setOnClickListener {
            var intent = Intent(context,videoPlayAct::class.java)
            intent.putExtra("link",list[position].videoLink)
            context.startActivity(intent)
        }
      holder.itemView.findViewById<ImageView>(R.id.imageView).setOnClickListener {
          var intent = Intent(context,Profile_Activity
          ::class.java)
          intent.putExtra("name",list[position].author)
          intent.putExtra("role",list[position].role)
          intent.putExtra("Auid",list[position].authourUid)
          intent.putExtra("img",list[position].authorImg)
          context.startActivity(intent)
      }

    }

    override fun getItemCount(): Int {
      return list.size
    }
}