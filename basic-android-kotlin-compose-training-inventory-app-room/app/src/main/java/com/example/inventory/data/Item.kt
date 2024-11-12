package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Menandakan bahwa kelas ini adalah entitas dalam database Room dan akan disimpan dalam tabel bernama "items"
@Entity(tableName = "items")
data class Item(
    // Menandai kolom "id" sebagai Primary Key dengan nilai yang dihasilkan secara otomatis (auto-increment)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,   // Kolom id sebagai identifikasi unik setiap item di dalam tabel "items"
    val name: String,   // Kolom untuk menyimpan nama item
    val price: Double,  // Kolom untuk menyimpan harga item
    val quantity: Int   // Kolom untuk menyimpan jumlah item
)

