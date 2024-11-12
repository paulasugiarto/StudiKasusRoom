package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * Kelas ItemEditViewModel adalah ViewModel yang digunakan untuk mengelola state UI
 * dari layar edit item. Kelas ini memanfaatkan savedStateHandle untuk mengakses data
 * yang disimpan sebelumnya, termasuk itemId.
*/
class ItemEditViewModel(
    // savedStateHandle digunakan untuk menyimpan dan mengakses data antar konfigurasi
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /**
     * itemUiState adalah state yang merepresentasikan UI state dari item yang sedang diedit.
     * mutableStateOf digunakan untuk membuat itemUiState dapat diamati dan
     * diperbarui secara reaktif di layar UI.
    */
    var itemUiState by mutableStateOf(ItemUiState())
        private set // private set untuk mencegah modifikasi langsung dari luar kelas

    /**
     * itemId digunakan untuk mengidentifikasi item yang akan diedit.
     * checkNotNull memastikan bahwa itemId tidak bernilai null,
     * sehingga aplikasi tidak akan crash jika itemId tidak ditemukan di savedStateHandle.
    */
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    /**
     * Fungsi validateInput digunakan untuk memvalidasi data input yang diisikan oleh pengguna.
     * Parameter uiState, yang secara default bernilai itemUiState.itemDetails, adalah data
     * yang akan divalidasi, meliputi nama, harga, dan kuantitas item.
    */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        // Validasi dilakukan dengan memeriksa apakah setiap field di uiState tidak kosong
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}
