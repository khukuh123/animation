package com.sample.animation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.animation.data.demoProduct
import com.sample.animation.ui.checkout.CheckoutScreen
import com.sample.animation.ui.detail.ProductDetailScreen
import com.sample.animation.ui.home.HomeScreen
import com.sample.animation.ui.success.CheckoutSuccessScreen

object Destinations {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val CHECKOUT = "checkout"
    const val SUCCESS = "success"
}

@Composable
fun AppNavGraph(
    cartCount: Int,
    onAddToCart: () -> Unit,
    onClearCart: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME,
        modifier = modifier
    ) {
        composable(Destinations.HOME) {
            HomeScreen(
                cartCount = cartCount,
                onBannerClick = { navController.navigate(Destinations.DETAIL) },
                onCartClick = { navController.navigate(Destinations.CHECKOUT) }
            )
        }
        composable(Destinations.DETAIL) {
            ProductDetailScreen(
                product = demoProduct,
                cartCount = cartCount,
                onBack = { navController.popBackStack() },
                onAddToCart = onAddToCart,
                onCheckout = { navController.navigate(Destinations.CHECKOUT) }
            )
        }
        composable(Destinations.CHECKOUT) {
            CheckoutScreen(
                cartCount = cartCount,
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Destinations.SUCCESS) {
                        popUpTo(Destinations.HOME) { inclusive = false }
                    }
                    onClearCart()
                }
            )
        }
        composable(Destinations.SUCCESS) {
            CheckoutSuccessScreen(
                onContinue = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.HOME) { inclusive = true }
                    }
                }
            )
            LaunchedEffect(Unit) {
                onClearCart()
            }
        }
    }
}
