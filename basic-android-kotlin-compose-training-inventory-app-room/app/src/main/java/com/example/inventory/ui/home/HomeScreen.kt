package com.example.inventory.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.item.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

/**
 * Mendefinisikan sebuah objek `HomeDestination` yang mengimplementasikan
 * NavigationDestination`. Objek ini akan digunakan untuk navigasi layar "Home".
*/
object HomeDestination : NavigationDestination {
    override val route = "home" // Rute untuk navigasi layar "Home"
    override val titleRes = R.string.app_name // Sumber teks untuk judul
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // Fungsi untuk navigasi ke layar memasukkan item baru
    navigateToItemEntry: () -> Unit,
    // Fungsi untuk navigasi ke layar memperbarui item dengan ID tertentu
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Mendefinisikan perilaku scroll untuk AppBar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Scaffold adalah struktur dasar layar yang memiliki AppBar dan FAB
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Menggunakan InventoryTopAppBar sebagai AppBar di bagian atas layar
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes), // Judul AppBar
                canNavigateBack = false, // Menentukan bahwa tidak bisa kembali
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            // Floating Action Button (FAB) untuk menambah item baru
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                // Ikon tambah pada FAB
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        // Menampilkan konten utama layar menggunakan fungsi HomeBody
        HomeBody(
            itemList = listOf(), // Daftar item kosong untuk contoh
            onItemClick = navigateToItemUpdate, // Fungsi ketika item diklik
            modifier = modifier.fillMaxSize(), // Memperluas konten ke layar penuh
            contentPadding = innerPadding, // Padding dalam konten
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Item>, // Daftar item yang akan ditampilkan
    onItemClick: (Int) -> Unit, // Fungsi untuk navigasi saat item diklik
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Layout vertikal untuk menampilkan daftar item
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        // Menampilkan pesan jika daftar item kosong
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description), // Pesan ketika daftar kosong
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            // Menampilkan daftar item menggunakan fungsi InventoryList
            InventoryList(
                itemList = itemList, // Daftar item untuk ditampilkan
                onItemClick = { onItemClick(it.id) }, // Fungsi ketika item diklik
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun InventoryList(
    itemList: List<Item>, // Daftar item untuk ditampilkan
    onItemClick: (Item) -> Unit, // Fungsi ketika item diklik
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    // Menampilkan daftar item dalam bentuk kolom yang bisa di-scroll
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        // Menampilkan setiap item dalam daftar menggunakan komponen InventoryItem
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(
                item = item, // Item yang sedang ditampilkan
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) } // Fungsi klik item
            )
        }
    }
}

@Composable
private fun InventoryItem(
    item: Item, // Item yang akan ditampilkan
    modifier: Modifier = Modifier
) {
    // Menampilkan setiap item dalam bentuk kartu dengan sedikit elevasi
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Layout vertikal untuk informasi item
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            // Baris pertama menampilkan nama item dan harga
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name, // Nama item
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f)) // Spacer untuk memisahkan nama dan harga
                Text(
                    text = item.formatedPrice(), // Harga item
                    style = MaterialTheme.typography.titleMedium
                )
            }
            // Menampilkan jumlah stok item
            Text(
                text = stringResource(R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// Fungsi pratinjau untuk menampilkan contoh HomeBody dengan item
@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        HomeBody(
            listOf(
                Item(1, "Game", 100.0, 20),
                Item(2, "Pen", 200.0, 30),
                Item(3, "TV", 300.0, 50)
            ),
            onItemClick = {}
        )
    }
}

// Fungsi pratinjau untuk menampilkan contoh HomeBody dengan daftar kosong
@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    InventoryTheme {
        HomeBody(listOf(), onItemClick = {})
    }
}

// Fungsi pratinjau untuk menampilkan satu InventoryItem
@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        InventoryItem(
            Item(1, "Game", 100.0, 20),
        )
    }
}