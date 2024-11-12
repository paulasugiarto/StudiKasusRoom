package com.example.inventory.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

/**
 * Objek ItemEntryDestination digunakan untuk mendefinisikan tujuan navigasi (destination)
 * menuju layar input atau entry item baru
*/
object ItemEntryDestination : NavigationDestination {
    // route berisi jalur navigasi menuju layar item entry
    override val route = "item_entry"
    // titleRes adalah resource judul yang akan ditampilkan pada toolbar layar
    override val titleRes = R.string.item_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEntryScreen(
    // navigateBack berfungsi untuk mengatur aksi kembali ke layar sebelumnya
    navigateBack: () -> Unit,
    // onNavigateUp digunakan untuk navigasi ke atas dalam hierarki layar
    onNavigateUp: () -> Unit,
    // canNavigateBack menentukan apakah navigasi kembali diizinkan atau tidak
    canNavigateBack: Boolean = true,
    // viewModel merupakan instance dari ItemEntryViewModel yang berfungsi mengelola UI state
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // coroutineScope digunakan untuk menjalankan fungsi asinkron, seperti saveItem()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        // topBar menampilkan InventoryTopAppBar sebagai toolbar di bagian atas layar
        topBar = {
            InventoryTopAppBar(
                // Mengambil judul dari titleRes yang ada di ItemEntryDestination
                title = stringResource(ItemEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        // Memanggil fungsi ItemEntryBody sebagai konten utama dari layar ini
        ItemEntryBody(
            itemUiState = viewModel.itemUiState,
            // Fungsi untuk meng-update state UI saat ada perubahan nilai item
            onItemValueChange = viewModel::updateUiState,
            // Fungsi yang akan dipanggil saat tombol save ditekan
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem() // Simpan data item ke dalam database atau repository
                    navigateBack() // Kembali ke layar sebelumnya setelah item disimpan
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()) // Mengizinkan konten di-scroll secara vertikal
                .fillMaxWidth()
        )
    }
}

@Composable
fun ItemEntryBody(
    // itemUiState berisi state UI dari item yang akan ditambahkan
    itemUiState: ItemUiState,
    // onItemValueChange adalah fungsi callback yang dipanggil saat nilai item berubah
    onItemValueChange: (ItemDetails) -> Unit,
    // onSaveClick adalah aksi yang akan dijalankan ketika tombol save ditekan
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        // Mengatur jarak antar elemen di dalam kolom dengan nilai padding besar
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Menampilkan form input item
        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        // Tombol simpan yang akan memanggil fungsi onSaveClick ketika ditekan
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid, // Tombol hanya aktif jika data valid
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action)) // Teks pada tombol
        }
    }
}

@Composable
fun ItemInputForm(
    // itemDetails berisi detail dari item yang akan dimasukkan, seperti nama, harga, dan kuantitas
    itemDetails: ItemDetails,
    modifier: Modifier = Modifier,
    // onValueChange adalah fungsi callback yang dipanggil saat ada perubahan pada field input
    onValueChange: (ItemDetails) -> Unit = {},
    enabled: Boolean = true // Mengatur apakah input diizinkan atau tidak
) {
    Column(
        modifier = modifier,
        // Mengatur jarak antar elemen di dalam kolom dengan nilai padding medium
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Field input untuk nama item
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = { onValueChange(itemDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Field input untuk harga item
        OutlinedTextField(
            value = itemDetails.price,
            onValueChange = { onValueChange(itemDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.item_price_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Field input untuk kuantitas item
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = { onValueChange(itemDetails.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.quantity_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Menampilkan teks informasi jika field input yang diperlukan harus diisi
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    // Fungsi preview ini menampilkan layar entry item dalam tema aplikasi tanpa menjalankan aplikasi sepenuhnya
    InventoryTheme {
        ItemEntryBody(
            itemUiState = ItemUiState(
                ItemDetails(name = "Item name", price = "10.00", quantity = "5")
            ),
            onItemValueChange = {},
            onSaveClick = {}
        )
    }
}
