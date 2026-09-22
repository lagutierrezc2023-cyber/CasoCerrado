package com.example.casocerrado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.casocerrado.navigation.AppNavigation
import com.example.casocerrado.ui.theme.CasoCerradoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CasoCerradoTheme {
                AppNavigation()
            }
        }
    }
}
