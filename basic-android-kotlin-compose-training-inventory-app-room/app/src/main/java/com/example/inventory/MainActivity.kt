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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.inventory.ui.theme.InventoryTheme

// MainActivity adalah kelas utama yang dijalankan saat aplikasi dibuka
class MainActivity : ComponentActivity() {

    // onCreate adalah metode yang dipanggil saat activity pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * enableEdgeToEdge digunakan untuk mengaktifkan tampilan edge-to-edge,
         * yaitu menghilangkan padding standar di sekitar layar (untuk aplikasi full-screen)
        */
        enableEdgeToEdge()

        /**
         * Memanggil onCreate dari kelas induk (ComponentActivity) untuk menjaga
         * fungsionalitas dasar activity
        */
        super.onCreate(savedInstanceState)

        // setContent digunakan untuk menetapkan konten UI yang akan ditampilkan pada activity ini
        setContent {
            // InventoryTheme adalah tema aplikasi yang diterapkan untuk seluruh tampilan UI
            InventoryTheme {

                /**
                 * Surface adalah komponen yang menyediakan latar belakang dengan pengaturan tertentu
                 * Menggunakan Modifier.fillMaxSize() untuk memastikan Surface mengisi layar sepenuhnya
                 * Menggunakan MaterialTheme.colorScheme.background sebagai warna latar belakang
                */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // InventoryApp adalah komponen UI utama yang akan ditampilkan di dalam Surface
                    InventoryApp()
                }
            }
        }
    }
}
