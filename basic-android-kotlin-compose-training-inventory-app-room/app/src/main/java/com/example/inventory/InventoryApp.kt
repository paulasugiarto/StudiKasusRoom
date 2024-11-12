package com.example.inventory

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.R.string
import com.example.inventory.ui.navigation.InventoryNavHost

/**
 * Fungsi InventoryApp digunakan sebagai entry point untuk aplikasi inventory
 * Di sini, navController digunakan untuk mengelola navigasi antar layar di dalam aplikasi
*/
@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    // Memanggil InventoryNavHost dengan navController sebagai parameter untuk mengatur host navigasi
    InventoryNavHost(navController = navController)
}

// Fungsi InventoryTopAppBar digunakan untuk menampilkan top bar (AppBar) pada layar aplikasi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    // title adalah teks judul yang ditampilkan pada AppBar
    title: String,
    // canNavigateBack menentukan apakah ikon navigasi kembali (back) perlu ditampilkan
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    // scrollBehavior menentukan perilaku scrolling dari AppBar
    scrollBehavior: TopAppBarScrollBehavior? = null,
    // navigateUp adalah fungsi yang akan dipanggil saat tombol back ditekan
    navigateUp: () -> Unit = {}
) {
    // CenterAlignedTopAppBar adalah AppBar yang judulnya berada di tengah
    CenterAlignedTopAppBar(
        title = { Text(title) }, // Menampilkan teks judul di tengah AppBar
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        // navigationIcon adalah ikon navigasi yang ditampilkan di AppBar
        navigationIcon = {
            // Jika canNavigateBack bernilai true, maka ikon kembali (back) akan ditampilkan
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack, // Menggunakan ikon panah kembali
                        contentDescription = stringResource(string.back_button) // Deskripsi ikon
                    )
                }
            }
        }
    )
}
