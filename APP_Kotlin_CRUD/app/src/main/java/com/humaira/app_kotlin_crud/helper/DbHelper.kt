package com.humaira.app_kotlin_crud.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.humaira.app_kotlin_crud.model.ModelBuku

class DbHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    fun closeDatabase(){
        if(database.isOpen && databaseOpen){
            database.close()
            databaseOpen = false

            Log.i("Database", "Database Closed")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                JUDUL_BUKU + " TEXT," +
                PENULIS_BUKU + " TEXT," +
                ISI_BUKU + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun getBukuById(bukuId : Int): ModelBuku {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $ID_COL = $bukuId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
        val judul = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL_BUKU))
        val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS_BUKU))
        val isi = cursor.getString(cursor.getColumnIndexOrThrow(ISI_BUKU))

        cursor.close()
        db.close()
        return ModelBuku(id, judul, penulis, isi)
    }

    fun deleteBuku(bukuId : Int){
        val db = writableDatabase
        val whereClause = "$ID_COL = ?"
        val whereArgs = arrayOf(bukuId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
    // This method is for adding data in our database
    fun addName(judul: String, penulis: String, isi: String) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(JUDUL_BUKU, judul)
        values.put(PENULIS_BUKU, penulis)
        values.put(ISI_BUKU, isi)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    //get all data
    fun getAllDataBuku() : List<ModelBuku>{
        val bukuList = mutableListOf<ModelBuku>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
            val judul = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL_BUKU))
            val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS_BUKU))
            val isi = cursor.getString(cursor.getColumnIndexOrThrow(ISI_BUKU))

            Log.d("Databasehelper", "Fecthed ID : $id, judulbuku: $judul")
            val nBuku = ModelBuku(id, judul, penulis, isi)
            bukuList.add(nBuku)
        }
        cursor.close()
        db.close()
        return bukuList
    }

    fun insertDataBuku(buku: ModelBuku) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(JUDUL_BUKU, buku.judul)
            put(PENULIS_BUKU, buku.penulis)
            put(ISI_BUKU, buku.isi)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateBuku(buku: ModelBuku){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(JUDUL_BUKU, buku.judul)
            put(PENULIS_BUKU, buku.penulis)
            put(ISI_BUKU, buku.isi)
        }
        val whereClause = "$ID_COL = ?"
        val whereArgs = arrayOf(buku.id.toString())//rubah id ke string

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }





//    fun getAllData() : MutableList<ModelMahasiswa>{
//        if(!databaseOpen){
//            database = INSTANCE.writableDatabase
//            databaseOpen = true // db nya kita open
//
//            Log.i("Database", "Database Open")
//        }
//
//        val data: MutableList<ModelMahasiswa> = ArrayList()
//        val cursor = database.rawQuery("SELECT * FROM" + TABLE_NAME, null)
//        cursor.use {cur ->
//            if(cursor.moveToFirst()){
//                do{
//                    val mahasiswa = ModelMahasiswa()
//                    mahasiswa.id = cur.getInt(cur.getColumnIndex(ID_COL))
//                    mahasiswa.nama = cur.getString(cur.getColumnIndex(NAMA_MAHASISWA))
//                    mahasiswa.nim = cur.getInt(cur.getColumnIndex(NIM))
//                    mahasiswa.jurusan = cur.getString(cur.getColumnIndex(JURUSAN))
//                    data.add(mahasiswa)
//                }while (cursor.moveToNext())
//            }
//        }
//        return data
//    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DB_BUKU"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "tb_buku"

        // below is the variable for id column
        val ID_COL = "id"
        val JUDUL_BUKU = "judul_buku"
        val PENULIS_BUKU = "penulis"
        val ISI_BUKU = "isi"

        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false
        private lateinit var INSTANCE: DbHelper
    }
}