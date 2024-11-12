package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
/**
 * Class HomeViewModel mewarisi kelas ViewModel.
 * Kelas ini digunakan untuk mengelola dan menyimpan data UI terkait Home secara efektif
 * sehingga dapat bertahan terhadap perubahan konfigurasi, seperti rotasi layar.
*/
class HomeViewModel() : ViewModel() {

    companion object {
        /**
         * TIMEOUT_MILLIS digunakan untuk menyimpan konstanta timeout dalam milidetik.
         * Dalam hal ini, nilainya diatur menjadi 5.000 milidetik (atau 5 detik).
         * Konstanta ini mungkin digunakan untuk membatasi waktu tunggu
         * dalam pengambilan data atau operasi lainnya.
        */
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Data class HomeUiState digunakan untuk menyimpan status UI pada layar Home.
 * Variabel itemList dalam kelas ini bertipe List<Item>, yang diinisialisasi dengan
 * daftar kosong secara default. itemList berfungsi untuk menyimpan daftar item
 * yang akan ditampilkan di UI, memungkinkan tampilan untuk menampilkan data yang
 * terkini dan sesuai dengan status terkini.
 */
data class HomeUiState(val itemList: List<Item> = listOf())

