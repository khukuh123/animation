package com.sample.animation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.sample.animation.data.PromoBanner
import kotlinx.coroutines.delay

@Composable
fun BannerSlider(
    banners: List<PromoBanner>,
    currentIndex: Int,
    onBannerClick: (PromoBanner) -> Unit,
    onIndicatorSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeBanner = banners[currentIndex]
    var breathe by remember { mutableStateOf(false) }

    LaunchedEffect(activeBanner) {
        while (true) {
            breathe = !breathe
            delay(1200)
        }
    }

    // TODO: 1.1 Simple animation for sliding and breathing banner effects.
    val scale = remember { mutableStateOf(1f) }
    val offset = remember { mutableStateOf(0f) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .graphicsLayer(
                    translationX = offset.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                )
                .clickable { onBannerClick(activeBanner) },
            colors = CardDefaults.cardColors(containerColor = activeBanner.color),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = activeBanner.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Discover great offers today",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            banners.forEachIndexed { index, _ ->
                // TODO: 1.2 Simple animation for page indicators effects.
                val indicatorScale = remember { mutableStateOf(1f) }
                val color = if (index == currentIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                Spacer(
                    modifier = Modifier
                        .size(10.dp)
                        .graphicsLayer(
                            scaleX = indicatorScale.value,
                            scaleY = indicatorScale.value
                        )
                        .clickable { onIndicatorSelect(index) }
                        .background(color, shape = RoundedCornerShape(50))
                )
            }
        }
    }
}
