package com.vanka.devbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainScreen : AppCompatActivity() {
    private var backVal =0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main_screen)
        LoadFrag(HomeFrag())
        findViewById<BottomNavigationView>(R.id.nav).setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.home->{
                    LoadFrag(HomeFrag())
                    true

                }
                R.id.upload->{
                    LoadFrag(UploadFrag())
                    true
                }
                R.id.library->{
                    LoadFrag(LibraryFrag())
                    true
                }
                R.id.ds->{
                    LoadFrag(DevShorts())
                    true
                }
                R.id.following->{
                    LoadFrag(Following())
                    true
                }




                else -> {
                    LoadFrag(HomeFrag())
                    true
                }
            }

        }
    }
    private fun LoadFrag(fragment: Fragment) {
        var load = supportFragmentManager.beginTransaction()
        load.replace(R.id.cont,fragment)
        load.commit()
    }


}