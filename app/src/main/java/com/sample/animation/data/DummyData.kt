package com.sample.animation.data

import androidx.compose.ui.graphics.Color

val promoBanners = listOf(
    PromoBanner(id = 1, title = "Midnight Sale", color = Color(0xFFE57373)),
    PromoBanner(id = 2, title = "Buy 1 Get 1", color = Color(0xFF64B5F6)),
    PromoBanner(id = 3, title = "Flash Deal", color = Color(0xFF81C784))
)

val demoProduct = Product(
    id = 1,
    name = "Stellar Wireless Headphones",
    price = 799_000,
    description = "Premium wireless headphone with noise cancellation.",
    specs = listOf("Bluetooth 5.3", "12h battery", "Type-C charging"),
    color = Color(0xFF90CAF9)
)
