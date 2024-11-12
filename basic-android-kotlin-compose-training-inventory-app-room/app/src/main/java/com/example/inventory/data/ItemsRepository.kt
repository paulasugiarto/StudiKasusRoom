package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    /**
     * Mengambil semua item yang tersedia dalam bentuk aliran data (stream).
     * Fungsi ini mengembalikan Flow yang berisi daftar item.
     * Setiap kali data diubah di sumber, Flow akan memberikan pembaruan
     * terbaru ke setiap observer yang mendengarkan aliran ini.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil item tertentu berdasarkan ID yang diberikan dalam bentuk aliran data (stream).
     * Fungsi ini mengembalikan Flow yang berisi item dengan ID yang sesuai atau null
     * jika item tidak ditemukan. Aliran ini akan memberikan pembaruan terbaru
     * jika data item berubah di sumber.
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyisipkan item baru ke dalam sumber data.
     * Karena ini operasi yang memodifikasi data, fungsi ini
     * dideklarasikan sebagai suspend agar dapat dijalankan
     * secara asynchronous tanpa menghambat thread utama.
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item yang diberikan dari sumber data.
     * Operasi ini dilakukan secara asynchronous dengan fungsi suspend
     * agar tidak mengganggu performa UI ketika dijalankan di thread utama.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui data item yang sudah ada dengan data baru.
     * Operasi ini dilakukan secara asynchronous agar perubahan
     * data dapat dilakukan tanpa mengganggu performa UI.
     */
    suspend fun updateItem(item: Item)
}