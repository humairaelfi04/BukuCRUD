package com.humaira.app_kotlin_crud

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailBuku : AppCompatActivity() {
    private lateinit var txtBuku : TextView
    private lateinit var txtJudulBuku : TextView
    private lateinit var txtPenulisBuku : TextView
    private lateinit var txtIsi : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_buku)

        txtBuku = findViewById(R.id.txtBuku)
        txtJudulBuku = findViewById(R.id.txtJudulBuku)
        txtPenulisBuku = findViewById(R.id.txtPenulisBuku)
        txtIsi = findViewById(R.id.txtIsi)


        val judulBuku = intent.getStringExtra("judul")
        val penulisBuku = intent.getStringExtra("penulis")
        val isiBuku = intent.getStringExtra("isi")

        txtJudulBuku.setText(judulBuku)
        txtPenulisBuku.setText(penulisBuku)
        txtIsi.setText(isiBuku)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}