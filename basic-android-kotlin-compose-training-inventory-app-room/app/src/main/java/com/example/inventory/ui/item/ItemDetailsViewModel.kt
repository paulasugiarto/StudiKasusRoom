package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository
/**
 * ViewModel bernama ItemDetailsViewModel yang bertugas untuk mengelola data terkait
 * tampilan item details. ViewModel ini menerima `savedStateHandle` sebagai parameter
 * untuk mengelola dan menyimpan state dari UI yang dapat dipertahankan
 * selama daur hidup aktivitas atau fragment.
*/
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Mengambil `itemId` dari `savedStateHandle` menggunakan kunci `itemIdArg`
     * dari `ItemDetailsDestination`. `itemId` bersifat wajib dan akan menghasilkan
     * exception jika nilainya `null`, sehingga digunakan `checkNotNull`.
    */
    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    /**
     * Objek `companion` yang berisi konstanta untuk `TIMEOUT_MILLIS`,
     * yang berfungsi sebagai batas waktu (timeout) dalam satuan milidetik.
    */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Data class `ItemDetailsUiState` yang merepresentasikan status tampilan UI
 * untuk item details. `outOfStock` adalah nilai boolean yang menunjukkan apakah
 * item tersebut habis atau tidak, dengan nilai default `true` (habis).
 * `itemDetails` adalah objek dari `ItemDetails` yang digunakan untuk menyimpan
 * informasi detail dari item yang ditampilkan.
*/
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)
