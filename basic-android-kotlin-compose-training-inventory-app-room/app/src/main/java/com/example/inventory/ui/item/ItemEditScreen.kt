package com.example.inventory.ui.item

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
/**
 * Objek ItemEditDestination didefinisikan sebagai tujuan navigasi (destination)
 * yang berfungsi untuk mengarahkan pengguna ke layar edit item
*/
object ItemEditDestination : NavigationDestination {
    // route adalah jalur navigasi untuk layar edit item
    override val route = "item_edit"
    // titleRes digunakan untuk menampilkan judul pada toolbar berdasarkan resource string
    override val titleRes = R.string.edit_item_title
    // itemIdArg adalah argumen yang digunakan dalam navigasi, berfungsi untuk mengidentifikasi item yang akan diedit
    const val itemIdArg = "itemId"
    // routeWithArgs mendefinisikan jalur navigasi dengan argumen itemId
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    // Parameter navigateBack berfungsi untuk kembali ke layar sebelumnya
    navigateBack: () -> Unit,
    // Parameter onNavigateUp digunakan untuk navigasi ke atas dalam hierarki layar
    onNavigateUp: () -> Unit,
    // Modifier adalah opsional untuk mengatur tampilan dan perilaku komponen
    modifier: Modifier = Modifier,
    // viewModel adalah instance dari ItemEditViewModel yang digunakan untuk mengelola UI state
    viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Scaffold menyediakan struktur dasar untuk layar, termasuk topBar, bottomBar, dan konten utama
    Scaffold(
        // topBar menampilkan InventoryTopAppBar sebagai toolbar di bagian atas layar
        topBar = {
            InventoryTopAppBar(
                // Menampilkan judul toolbar berdasarkan titleRes yang ada di ItemEditDestination
                title = stringResource(ItemEditDestination.titleRes),
                // Mengatur apakah navigasi kembali (back navigation) diizinkan
                canNavigateBack = true,
                // Mengatur tindakan ketika tombol "up" ditekan
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        // ItemEntryBody adalah konten utama yang berisi antarmuka untuk mengedit item
        ItemEntryBody(
            // itemUiState adalah state dari item yang akan diedit, didapat dari viewModel
            itemUiState = viewModel.itemUiState,
            // onItemValueChange mengatur fungsi ketika ada perubahan pada item yang diinput
            onItemValueChange = { },
            // onSaveClick adalah aksi yang akan dijalankan ketika tombol simpan ditekan
            onSaveClick = { },
            modifier = Modifier
                // Padding disesuaikan dengan inner padding dari Scaffold
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                // Konten layar dapat di-scroll secara vertikal
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEditScreenPreview() {
    /**
     * Fungsi preview ini digunakan untuk menampilkan tampilan layar edit item
     * tanpa perlu menjalankan aplikasi sepenuhnya
    */
    InventoryTheme {
        // Memanggil ItemEditScreen dengan navigateBack dan onNavigateUp yang diabaikan
        ItemEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
