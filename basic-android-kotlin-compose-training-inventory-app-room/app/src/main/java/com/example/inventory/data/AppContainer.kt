package com.example.inventory.data

import android.content.Context

/**
* Interface AppContainer bertindak sebagai
* sebuah container untuk menyediakan akses ke objek ItemsRepository.
* Dengan mendefinisikan ItemsRepository sebagai
* properti dalam AppContainer, kita dapat mengelola dependensi dengan lebih mudah.
*/
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
* Class AppDataContainer mengimplementasikan AppContainer
* dan digunakan untuk menyediakan instance dari ItemsRepository.
* Class ini menerima sebuah Context sebagai parameter untuk
* mengakses resource atau database aplikasi.
*/
class AppDataContainer(private val context: Context) : AppContainer {

    /**
    * itemsRepository diinisialisasi menggunakan delegasi "lazy",
    * yang berarti instance OfflineItemsRepository
    * hanya akan dibuat ketika itemsRepository pertama kali diakses.
     */
    override val itemsRepository: ItemsRepository by lazy {
        /**
        * Membuat instance OfflineItemsRepository
        * dengan mengambil itemDao dari InventoryDatabase.
        * getDatabase(context) memberikan instance database,
        * dan itemDao() adalah akses ke DAO yang dibutuhkan untuk mengelola data.
        */
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

