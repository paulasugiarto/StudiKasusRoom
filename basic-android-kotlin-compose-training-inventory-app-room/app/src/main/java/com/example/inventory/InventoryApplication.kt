/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory

import android.app.Application
import com.example.inventory.data.AppContainer
import com.example.inventory.data.AppDataContainer

/**
 * Kelas InventoryApplication merupakan kelas aplikasi utama yang mewarisi kelas Application.
 * Kelas ini akan digunakan untuk menyimpan dan mengelola state global aplikasi,
 * seperti inisialisasi dependency injection atau container data.
*/
class InventoryApplication : Application() {

    /**
     * Deklarasi variabel container sebagai AppContainer, yang akan digunakan
     * untuk mengelola semua dependency atau data yang dibutuhkan oleh aplikasi.
     * Variabel ini diinisialisasi secara lazy, sehingga hanya dibuat saat diperlukan.
    */
    lateinit var container: AppContainer

    /**
     * Fungsi onCreate() akan dipanggil ketika aplikasi pertama kali dijalankan.
     * Di dalam fungsi ini, kita menginisialisasi container dengan membuat instance
     * dari AppDataContainer, yang merupakan implementasi dari AppContainer.
    */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this) // Inisialisasi container
    }
}

