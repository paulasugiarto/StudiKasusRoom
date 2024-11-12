package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Kelas OfflineItemsRepository merupakan implementasi dari interface ItemsRepository.
 * Kelas ini menggunakan dependency injection dengan menerima parameter itemDao yang merupakan instance dari ItemDao.
 * Kelas ini bertanggung jawab untuk mengelola operasi database secara offline dengan menggunakan fungsi yang didefinisikan pada itemDao.
 */
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {

    /**
     * Fungsi getAllItemsStream() mengembalikan aliran (Flow) daftar item dari database.
     * Aliran ini memungkinkan pemantauan secara real-time terhadap perubahan data di tabel item.
     * Setiap perubahan pada tabel akan secara otomatis memperbarui daftar item yang diterima.
     */
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    /**
     * Fungsi getItemStream(id: Int) mengembalikan aliran (Flow) dari item tertentu berdasarkan ID yang diberikan.
     * Jika item dengan ID tersebut ada, maka data item akan dikembalikan, tetapi jika tidak ada, hasilnya adalah null.
     * Aliran ini juga akan diperbarui jika ada perubahan pada item terkait di database.
     */
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    /**
     * Fungsi insertItem(item: Item) menambahkan item baru ke dalam database secara asinkron.
     * Fungsi ini menggunakan operasi suspensi (suspend) yang memungkinkan pemanggilan
     * fungsi ini tanpa mengganggu proses utama (main thread).
     */
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    /**
     * Fungsi deleteItem(item: Item) menghapus item tertentu dari database secara asinkron.
     * Sama seperti fungsi insertItem, operasi ini juga menggunakan suspend untuk menghindari blokir pada main thread.
     */
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /**
     * Fungsi updateItem(item: Item) memperbarui data item tertentu di database secara asinkron.
     * Fungsi ini juga menggunakan suspend, memastikan proses pembaruan berjalan secara efisien tanpa membebani main thread.
     */
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}
