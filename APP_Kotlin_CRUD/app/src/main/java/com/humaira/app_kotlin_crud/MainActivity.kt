package com.humaira.app_kotlin_crud

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.humaira.app_kotlin_crud.adapter.BukuAdapter
import com.humaira.app_kotlin_crud.helper.DbHelper
import com.humaira.app_kotlin_crud.screen_page.TambahDataBukuActivity
import com.humaira.app_kotlin_crud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : DbHelper
    private lateinit var bukuAdapter : BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)
        bukuAdapter = BukuAdapter(db.getAllDataBuku(), this)

        binding.rvDataBuku.layoutManager = LinearLayoutManager(this)
        binding.rvDataBuku.adapter = bukuAdapter

        binding.btnPageTambahData.setOnClickListener{
            val intent = Intent(this, TambahDataBukuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val newBuku = db.getAllDataBuku()
        bukuAdapter.refreshData(newBuku)
    }
}