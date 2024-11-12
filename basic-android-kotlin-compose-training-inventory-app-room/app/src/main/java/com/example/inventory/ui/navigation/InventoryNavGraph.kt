package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen
import com.example.inventory.ui.item.ItemEntryDestination
import com.example.inventory.ui.item.ItemEntryScreen

@Composable
fun InventoryNavHost(
    // navController mengontrol navigasi antar layar, sedangkan modifier digunakan untuk menambahkan modifikasi tampilan
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    // NavHost adalah komponen yang mengatur rute navigasi dan tampilan layar berdasarkan route yang dipilih
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route, // Menentukan layar pertama yang akan tampil
        modifier = modifier
    ) {
        // Menangani navigasi ke HomeScreen dengan route HomeDestination.route
        composable(route = HomeDestination.route) {
            // Ketika berada di HomeScreen, pengguna bisa menavigasi ke ItemEntry atau ItemUpdate
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                }
            )
        }
        // Menangani navigasi ke layar ItemEntry saat route sesuai dengan ItemEntryDestination.route
        composable(route = ItemEntryDestination.route) {
            // ItemEntryScreen dapat kembali ke layar sebelumnya atau ke atas hierarki
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() }, // Kembali ke layar sebelumnya
                onNavigateUp = { navController.navigateUp() } // Navigasi ke layar sebelumnya dalam hierarki
            )
        }
        // Menangani navigasi ke layar ItemDetails dengan parameter itemId yang diteruskan
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType // Menyatakan bahwa itemId adalah tipe Integer
            })
        ) {
            // ItemDetailsScreen memungkinkan navigasi ke layar edit item
            ItemDetailsScreen(
                navigateToEditItem = {
                    navController.navigate("${ItemEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() } // Kembali ke layar sebelumnya
            )
        }
        // Menangani navigasi ke layar ItemEdit dengan itemId sebagai argumen
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType // Menggunakan tipe Integer untuk itemId
            })
        ) {
            // ItemEditScreen mengizinkan pengguna kembali ke layar sebelumnya atau ke atas hierarki
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}