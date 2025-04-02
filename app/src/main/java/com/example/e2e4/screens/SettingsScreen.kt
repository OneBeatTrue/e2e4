package com.example.e2e4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e2e4.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var soundEnabled by rememberSaveable { mutableStateOf(true) }
    val soundIcon = remember { derivedStateOf {
        if (soundEnabled) R.drawable.baseline_volume_up_24 else R.drawable.baseline_volume_off_24
    } }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Настройки") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text("Звук")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = soundEnabled,
                    onCheckedChange = {
                        soundEnabled = it
                    }
                )
                Icon(
                    painter = painterResource(id = soundIcon.value),
                    contentDescription = "Sound Icon"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}

