package com.example.inventory.ui.item

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

// Objek tujuan navigasi untuk layar detail item
object ItemDetailsDestination : NavigationDestination {
    override val route = "item_details" // Rute untuk layar ini
    override val titleRes = R.string.item_detail_title // Judul untuk layar ini
    const val itemIdArg = "itemId" // Argumen ID item
    val routeWithArgs = "$route/{$itemIdArg}" // Rute dengan argumen ID item
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit, // Fungsi navigasi ke layar edit item
    navigateBack: () -> Unit, // Fungsi untuk kembali ke layar sebelumnya
    modifier: Modifier = Modifier // Modifier untuk menyesuaikan tampilan
) {
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(ItemDetailsDestination.titleRes), // Judul di AppBar
                canNavigateBack = true, // Mengaktifkan tombol kembali
                navigateUp = navigateBack // Menghubungkan tombol kembali dengan navigasi kembali
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(0) }, // Aksi saat tombol ditekan
                shape = MaterialTheme.shapes.medium, // Bentuk tombol
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, // Ikon edit untuk tombol
                    contentDescription = stringResource(R.string.edit_item_title), // Deskripsi ikon
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ItemDetailsBody(
            itemDetailsUiState = ItemDetailsUiState(), // Status UI untuk detail item
            onSellItem = { }, // Aksi untuk tombol 'Jual'
            onDelete = { }, // Aksi untuk tombol 'Hapus'
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()) // Tampilan bisa digulir secara vertikal
        )
    }
}

// Fungsi komposisi untuk menampilkan konten utama layar detail item
@Composable
private fun ItemDetailsBody(
    itemDetailsUiState: ItemDetailsUiState, // Status UI untuk detail item
    onSellItem: () -> Unit, // Fungsi yang dipanggil saat tombol 'Jual' ditekan
    onDelete: () -> Unit, // Fungsi yang dipanggil saat tombol 'Hapus' ditekan
    modifier: Modifier = Modifier // Modifier untuk menyesuaikan tampilan
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)) // Jarak antar elemen vertikal
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) } // Status apakah dialog konfirmasi diaktifkan

        ItemDetails(
            item = itemDetailsUiState.itemDetails.toItem(),
            modifier = Modifier.fillMaxWidth() // Mengisi seluruh lebar tampilan
        )
        Button(
            onClick = onSellItem, // Aksi tombol 'Jual'
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = true
        ) {
            Text(stringResource(R.string.sell)) // Teks pada tombol 'Jual'
        }
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true }, // Aksi tombol 'Hapus'
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete)) // Teks pada tombol 'Hapus'
        }
        if (deleteConfirmationRequired) { // Jika dialog konfirmasi diaktifkan
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false // Menutup dialog setelah konfirmasi
                    onDelete() // Memanggil aksi hapus
                },
                onDeleteCancel = { deleteConfirmationRequired = false }, // Membatalkan hapus
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

// Komponen untuk menampilkan detail item dalam bentuk kartu
@Composable
fun ItemDetails(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ItemDetailsRow(
                labelResID = R.string.item, // Label untuk item
                itemDetail = item.name, // Detail nama item
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailsRow(
                labelResID = R.string.quantity_in_stock, // Label untuk jumlah item
                itemDetail = item.quantity.toString(), // Detail jumlah
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailsRow(
                labelResID = R.string.price, // Label untuk harga item
                itemDetail = item.formatedPrice(), // Detail harga yang diformat
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

// Komponen untuk menampilkan baris label dan detail item
@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID)) // Menampilkan label
        Spacer(modifier = Modifier.weight(1f)) // Ruang kosong antara label dan detail
        Text(text = itemDetail, fontWeight = FontWeight.Bold) // Menampilkan detail dengan huruf tebal
    }
}

// Dialog konfirmasi hapus item
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Tidak ada aksi saat dialog ditutup */ },
        title = { Text(stringResource(R.string.attention)) }, // Judul dialog
        text = { Text(stringResource(R.string.delete_question)) }, // Teks pertanyaan hapus
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no)) // Teks tombol 'Tidak'
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes)) // Teks tombol 'Ya'
            }
        }
    )
}