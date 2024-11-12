package com.example.inventory.ui.navigation

/**
 * Interface NavigationDestination mendefinisikan kontrak untuk setiap tujuan navigasi
 * dalam aplikasi yang menggunakan sistem navigasi. Setiap tujuan harus memiliki
 * informasi tentang jalur (route) dan judul (title) yang digunakan dalam navigasi.
*/
interface NavigationDestination {
    /**
     * 'route' adalah sebuah String yang menyimpan jalur atau path yang digunakan
     * untuk navigasi ke tujuan ini. Jalur ini digunakan dalam sistem routing untuk
     * menentukan ke mana aplikasi akan berpindah ketika navigasi dilakukan.
    */
    val route: String

    /**
     * 'titleRes' adalah referensi ke resource ID (integer) yang menyimpan string
     * untuk judul yang akan ditampilkan di layar tujuan ini. Ini digunakan untuk
     * menampilkan judul di bagian atas layar (biasanya di AppBar atau toolbar).
    */
    val titleRes: Int
}

