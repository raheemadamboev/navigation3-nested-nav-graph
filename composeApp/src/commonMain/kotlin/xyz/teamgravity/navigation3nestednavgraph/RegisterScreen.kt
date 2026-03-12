package xyz.teamgravity.navigation3nestednavgraph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    onNavigateHome: () -> Unit,
    sharedViewmodel: AuthSharedViewModel,
    viewmodel: RegisterViewModel = viewModel { RegisterViewModel() }
) {
    val sharedCount by sharedViewmodel.count.collectAsStateWithLifecycle()
    val count by viewmodel.count.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Shared count: $sharedCount"
            )
            Button(
                onClick = sharedViewmodel::onIncrement
            ) {
                Text(
                    text = "Increment shared count"
                )
            }
            Text(
                text = "Local count: $count"
            )
            Button(
                onClick = viewmodel::onIncrement
            ) {
                Text(
                    text = "Increment local count"
                )
            }
            Button(
                onClick = onNavigateHome
            ) {
                Text(
                    text = "Register"
                )
            }
        }
    }
}