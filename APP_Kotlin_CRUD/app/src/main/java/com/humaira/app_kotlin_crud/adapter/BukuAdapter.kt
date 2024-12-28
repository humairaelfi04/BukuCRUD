package com.humaira.app_kotlin_crud.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.humaira.app_kotlin_crud.DetailBuku
import com.humaira.app_kotlin_crud.R
import com.humaira.app_kotlin_crud.helper.DbHelper
import com.humaira.app_kotlin_crud.model.ModelBuku
import com.humaira.app_kotlin_crud.screen_page.UpdateBukuActivity

class BukuAdapter(

    private var listBuku: List<ModelBuku>,
    private val context: Context
) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    private val db : DbHelper = DbHelper(context)
    class BukuViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtJudul : TextView = itemView.findViewById(R.id.txtItemJudulBuku)
        val txtIsi : TextView = itemView.findViewById(R.id.txtItemIsi)
        val txtPenulis : TextView = itemView.findViewById(R.id.txtItemPenulis)
        val cardBuku : CardView = itemView.findViewById(R.id.cardBuku)

        val btnEdit : ImageView = itemView.findViewById(R.id.btnEditItem)
        val btnDelete : ImageView = itemView.findViewById(R.id.btnDeleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_buku,
            parent, false
        )
        return BukuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBuku.size
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val nBuku = listBuku[position]
        holder.txtJudul.text = nBuku.judul
        holder.txtPenulis.text = nBuku.penulis
        holder.txtIsi.text = nBuku.isi
        holder.btnDelete.setOnClickListener(){
            db.deleteBuku(nBuku.id)
            refreshData(db.getAllDataBuku())
            Toast.makeText(holder.itemView.context,
                "Berhasil delete data ${nBuku.judul}", Toast.LENGTH_LONG
            ).show()
        }
        //bagi yg sudah bisa delete data kirim ke WA

        //edit data
        holder.btnEdit.setOnClickListener(){
            //perlu bikin 1 lagi activity edit
                val intent = Intent(holder.itemView.context, UpdateBukuActivity::class.java).apply {
                putExtra("id_buku", nBuku.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.cardBuku.setOnClickListener(){
            val intent = Intent(context, DetailBuku::class.java)

            intent.putExtra("judul", listBuku[position].judul)
            intent.putExtra("penulis", listBuku[position].penulis)
            intent.putExtra("isi", listBuku[position].isi)
            context.startActivity(intent)

            Toast.makeText(context, listBuku[position].judul, Toast.LENGTH_SHORT).show()
        }


    }

    //ini untuk refresh data
    fun refreshData(newBuku: List<ModelBuku>){
        listBuku = newBuku
        notifyDataSetChanged()
    }
}