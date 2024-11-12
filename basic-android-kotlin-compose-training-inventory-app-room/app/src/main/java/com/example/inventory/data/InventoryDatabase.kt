package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
* Menggunakan anotasi @Database untuk menentukan bahwa ini adalah database Room.
* 'entities' berisi daftar entitas yang dimiliki database ini, yaitu Item.
* 'version' adalah versi database, yang digunakan Room untuk mengelola migrasi.
* 'exportSchema' menentukan apakah skema database perlu diekspor
* untuk tujuan dokumentasi (false di sini untuk menyederhanakan).
*/
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /**
    * Fungsi abstrak untuk mendapatkan akses
    * ke DAO (Data Access Object) yang terkait dengan database ini.
    * ItemDao akan digunakan untuk operasi CRUD (Create, Read, Update, Delete) pada entitas Item.
    */
    abstract fun itemDao(): ItemDao

    companion object {
        /**
        * Instance ini digunakan untuk menyimpan satu instance
        * dari InventoryDatabase agar bersifat singleton.
        * @Volatile memastikan perubahan instance langsung terlihat di semua thread.
        */
        @Volatile
        private var Instance: InventoryDatabase? = null

        /**
        * Fungsi untuk mendapatkan instance singleton dari InventoryDatabase.
        * Jika instance belum ada, akan dibuat menggunakan synchronized
        * untuk mencegah pembuatan ganda di thread berbeda.
        */
        fun getDatabase(context: Context): InventoryDatabase {
            // Mengembalikan instance yang ada, atau membangunnya jika belum ada.
            return Instance ?: synchronized(this) {
                // Membuat database dengan databaseBuilder, nama database yang digunakan adalah "item_database".
                Room.databaseBuilder(
                    context.applicationContext,            // Context aplikasi, penting untuk menghindari kebocoran memori
                    InventoryDatabase::class.java,         // Kelas database yang sedang dibangun
                    "item_database"                        // Nama database
                ).build().also { Instance = it }           // Simpan instance yang dibangun ke variabel Instance
            }
        }
    }
}