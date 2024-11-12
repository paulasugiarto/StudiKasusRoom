package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/**
 * Kelas ItemEntryViewModel bertanggung jawab mengelola state UI (User Interface)
 * untuk layar entry item dan berinteraksi dengan ItemsRepository
*/
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * itemUiState adalah state UI yang digunakan untuk menampung data item yang sedang diinput,
     * termasuk validasi apakah data yang diinput valid atau tidak
    */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * updateUiState memperbarui itemUiState dengan data item baru yang diberikan melalui itemDetails
     * serta melakukan validasi input data item
    */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState = ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /**
     * saveItem digunakan untuk menyimpan item ke dalam repository,
     * namun hanya akan menyimpan jika data valid
    */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    /**
     * validateInput berfungsi untuk memvalidasi input data item.
     * Nama, harga, dan kuantitas harus tidak kosong agar input dianggap valid
    */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

// ItemUiState adalah kelas data untuk menyimpan data item saat ini dan status validasinya
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(), // Menyimpan detail data item
    val isEntryValid: Boolean = false // Menyimpan status validasi data item
)

// ItemDetails menyimpan informasi item yang terdiri dari id, nama, harga, dan kuantitas
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/**
 * Fungsi ekstensi untuk mengonversi ItemDetails menjadi Item.
 * Fungsi ini mengonversi harga ke tipe Double dan kuantitas ke tipe Int
*/
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

// Fungsi ekstensi untuk memformat harga item menjadi format mata uang lokal
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Fungsi ekstensi untuk mengonversi Item menjadi ItemUiState
 * dengan menyalin detail item dan menentukan status validasi input
*/
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Fungsi ekstensi untuk mengonversi Item menjadi ItemDetails
 * berfungsi untuk memudahkan pengisian ulang data item saat diperlukan
*/
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
