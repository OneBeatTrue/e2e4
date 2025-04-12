package com.example.e2e4.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.models.Player
import com.example.domain.usecase.GetPlayerUseCase
import com.example.data.repository.PlayerRepositoryImpl
import com.example.data.storage.InMemoryUserStorage
import com.example.domain.models.GetOrCreatePlayerParam

@Composable
fun HomeScreen(getPlayerUseCase: GetPlayerUseCase) {
    var player = Player("", 0, 0)

    Scaffold { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (title, textField, button, info) = createRefs()


            Text(
                text = "Добро пожаловать в E2E4!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            var name by rememberSaveable { mutableStateOf("") }
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Пользователь") },
                modifier = Modifier.constrainAs(textField) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                onClick = {
                    if (name.isNotEmpty()) {
                        player = getPlayerUseCase.execute(
                            GetOrCreatePlayerParam(
                                name
                            )
                        )
                        name = ""
                    }
                },
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(textField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text("Войти")
            }

            Text(
                text = if (player.name.isNotEmpty()) "${player.name}\nПобеды: ${player.wins}  Поражения: ${player.losses}" else "",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(info) {
                    top.linkTo(button.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(GetPlayerUseCase(PlayerRepositoryImpl(InMemoryUserStorage())))
}