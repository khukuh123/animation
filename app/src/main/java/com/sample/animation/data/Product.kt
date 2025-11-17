package com.sample.animation.data

import androidx.compose.ui.graphics.Color

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val specs: List<String>,
    val color: Color
)
