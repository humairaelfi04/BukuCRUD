package com.humaira.app_kotlin_crud.screen_page


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.humaira.app_kotlin_crud.databinding.ActivityUpdateBukuBinding
import com.humaira.app_kotlin_crud.helper.DbHelper
import com.humaira.app_kotlin_crud.model.ModelBuku

class UpdateBukuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBukuBinding
    private lateinit var db : DbHelper
    private var bukuId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        bukuId = intent.getIntExtra("id_buku", -1)
        if(bukuId == -1){
            finish()
            return
        }
        //proses menampilkan data ke edit text di edit view

        val buku = db.getBukuById(bukuId)
        binding.etEditJudul.setText(buku.judul)
        binding.etEditPenulis.setText(buku.penulis)
        binding.etEditIsiBuku.setText(buku.isi)

        //update dari button
        binding.btnEditBuku.setOnClickListener(){
            val newJudul = binding.etEditJudul.text.toString()
            val newPenulis =binding.etEditPenulis.text.toString()
            val newIsi = binding.etEditIsiBuku.text.toString()

            val updatedBuku = ModelBuku(bukuId, newJudul, newPenulis, newIsi)
            db.updateBuku(updatedBuku)
            finish()
            Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show()
        }
    }
}