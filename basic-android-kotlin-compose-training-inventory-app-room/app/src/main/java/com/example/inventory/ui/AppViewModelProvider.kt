package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    /**
     * Factory adalah objek yang menggunakan viewModelFactory untuk membuat instance ViewModel
     * yang diperlukan di dalam aplikasi. Dengan menggunakan viewModelFactory, kita dapat
     * mengatur inisialisasi ViewModel tertentu sesuai kebutuhan.
    */
    val Factory = viewModelFactory {
        // Menambahkan initializer untuk ItemEditViewModel dengan memanfaatkan SavedStateHandle
        initializer {
            ItemEditViewModel(
                /**
                 * createSavedStateHandle digunakan untuk menyimpan state aplikasi secara aman
                 * agar bisa dipulihkan saat konfigurasi berubah (misalnya rotasi layar)
                */
                this.createSavedStateHandle()
            )
        }

        /**
         * Inisialisasi untuk ItemEntryViewModel. Mengambil itemRepository dari container
         * aplikasi InventoryApplication untuk mengakses data item dan menyimpannya
        */
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        /**
         * Menambahkan initializer untuk ItemDetailsViewModel yang juga memerlukan
         * SavedStateHandle agar state dapat dipulihkan pada saat perubahan konfigurasi
        */
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        // Inisialisasi untuk HomeViewModel, yang tidak memerlukan argumen khusus
        initializer {
            HomeViewModel()
        }
    }
}

/**
 * Fungsi extension CreationExtras untuk mendapatkan instance dari InventoryApplication
 * Fungsi ini memungkinkan ViewModel mengakses aplikasi secara global, terutama untuk mendapatkan
 * repository dan komponen aplikasi lainnya.
*/
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
