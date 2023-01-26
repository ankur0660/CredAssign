package com.example.cred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cred.stackview.StackViewComp
import com.example.cred.stackview.rememberStackViewState
import com.example.cred.ui.theme.CredTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CredTheme {
                // A surface container using the 'background' color from the theme
                StackViewComp(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize(),
                    state = rememberStackViewState(maxSlot = 3)
                ) { index, state ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when (index) {
                                0 -> Color.Red
                                1 -> Color.Yellow
                                2 -> Color.Black
                                3 -> Color.Cyan
                                else -> Color.White
                            }
                        ))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CredTheme {
        Greeting("Android")
    }
}