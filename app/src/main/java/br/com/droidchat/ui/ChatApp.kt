package br.com.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.droidchat.navigation.ChatNavHost
import br.com.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatApp() {
    DroidChatTheme {
        Scaffold (
            bottomBar = {}
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ChatNavHost()
            }
        }
    }
}