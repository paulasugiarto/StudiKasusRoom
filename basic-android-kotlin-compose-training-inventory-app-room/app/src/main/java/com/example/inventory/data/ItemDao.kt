package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    /**
     * Menyisipkan item baru ke dalam database.
     * Jika item yang akan ditambahkan memiliki ID yang sama
     * dengan item yang sudah ada, maka data baru akan diabaikan
     * dan data yang lama tetap dipertahankan (menggunakan OnConflictStrategy.IGNORE).
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /**
     * Memperbarui data item yang ada di database.
     * Method ini digunakan untuk mengganti data item yang sudah ada
     * dengan data yang baru berdasarkan ID item tersebut.
     */
    @Update
    suspend fun update(item: Item)

    /**
     * Menghapus item yang ditentukan dari database.
     * Method ini akan menghapus data berdasarkan objek item yang diberikan.
     */
    @Delete
    suspend fun delete(item: Item)

    /**
     * Mengambil data item spesifik dari database berdasarkan ID item.
     * Mengembalikan Flow yang berisi objek item yang sesuai,
     * sehingga setiap perubahan pada item akan terupdate secara otomatis.
     */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /**
     * Mengambil seluruh data item dari database dan mengurutkannya
     * berdasarkan nama secara ascending (ASC).
     * Mengembalikan Flow yang berisi daftar item, sehingga setiap
     * perubahan pada daftar item akan terupdate secara otomatis.
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}
