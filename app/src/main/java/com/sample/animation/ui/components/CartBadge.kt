package com.sample.animation.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CartBadge(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: 3.1 Content animation for number transitions with slide, fade, and scale.
    BadgedBox(
        badge = {
            if (count > 0) {
                Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                    /*
                    * TODO: 3.2 Wrap this content into content animation where targetState is count with slide, fade and scale
                    * */
                    Box(modifier = Modifier.size(16.dp), contentAlignment = Alignment.Center) {
                        Text(text = "$count", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        },
        modifier = modifier
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart"
            )
        }
    }
}






















































// Pssst.... Use (slideInVertically { it } + fadeIn(animationSpec = tween(300)) + scaleIn()) to enter,
// and vice versa for transition (don't forget to combine them 'with together')