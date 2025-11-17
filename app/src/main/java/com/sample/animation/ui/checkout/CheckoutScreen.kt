package com.sample.animation.ui.checkout

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.sample.animation.ui.components.CartBadge
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class CheckoutState { Idle, Processing, Success, Failed }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    cartCount: Int,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(CheckoutState.Idle) }
    val scope = rememberCoroutineScope()

    val transition = updateTransition(targetState = state, label = "CheckoutTransition")
    val backgroundColor by transition.animateColor(label = "BackgroundColor") { target ->
        when (target) {
            CheckoutState.Idle -> MaterialTheme.colorScheme.surface
            CheckoutState.Processing -> MaterialTheme.colorScheme.secondaryContainer
            CheckoutState.Success -> Color(0xFFB2FF59)
            CheckoutState.Failed -> Color(0xFFFFCDD2)
        }
    }
    val cornerRadius by transition.animateFloat(label = "CornerRadius") { target ->
        when (target) {
            CheckoutState.Idle -> 24f
            CheckoutState.Processing -> 32f
            CheckoutState.Success -> 20f
            CheckoutState.Failed -> 18f
        }
    }
    val scale by transition.animateFloat(label = "Scale") { target ->
        when (target) {
            CheckoutState.Processing -> 1.05f
            CheckoutState.Success -> 1.02f
            CheckoutState.Failed -> 0.98f
            else -> 1f
        }
    }
    val rotation by transition.animateFloat(label = "Rotation") { target ->
        when (target) {
            CheckoutState.Processing -> 2f
            CheckoutState.Failed -> -2f
            else -> 0f
        }
    }

    val shake = remember { Animatable(0f) }
    LaunchedEffect(state) {
        if (state == CheckoutState.Failed) {
            shake.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 500
                    -10f at 0
                    10f at 100
                    -6f at 200
                    3f at 300
                    0f at 500
                }
            )
        } else {
            shake.snapTo(0f)
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TopAppBar(
                title = { Text(text = "Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    CartBadge(count = cartCount, onClick = {})
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        translationX = shake.value,
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = rotation
                    )
                    .clip(RoundedCornerShape(cornerRadius.dp))
                    .padding(4.dp),
                color = backgroundColor,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Status: ${state.name}", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = when (state) {
                            CheckoutState.Idle -> "Ready to process your payment."
                            CheckoutState.Processing -> "Processing payment, please wait..."
                            CheckoutState.Success -> "Payment approved!"
                            CheckoutState.Failed -> "Payment failed. Please try again."
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        if (state == CheckoutState.Processing) return@Button
                        scope.launch {
                            state = CheckoutState.Processing
                            delay(2500)
                            state = if (Random.nextFloat() <= 0.8f) CheckoutState.Success else CheckoutState.Failed
                            if (state == CheckoutState.Success) {
                                delay(600)
                                onSuccess()
                            }
                        }
                    },
                    enabled = state != CheckoutState.Success,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = if (state == CheckoutState.Processing) "Processing..." else "Pay Now")
                }
            }
        }
    }
}
