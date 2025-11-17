package com.sample.animation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.sample.animation.navigation.AppNavGraph
import com.sample.animation.theme.AnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTheme {
                var cartCount by rememberSaveable { mutableStateOf(0) }
                Surface {
                    AppNavGraph(
                        cartCount = cartCount,
                        onAddToCart = { cartCount++ },
                        onClearCart = { cartCount = 0 }
                    )
                }
            }
        }
    }
}
