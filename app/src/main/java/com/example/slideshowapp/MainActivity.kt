package com.example.slideshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.slideshowapp.ui.theme.SlideshowAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlideshowAppTheme {
                SlideshowScreen()
            }
        }
    }
}

@Composable
fun SlideshowScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    var textInput by remember { mutableStateOf("") }

    SlideshowContent(
        currentIndex = currentIndex,
        textInput = textInput,
        onNext = { currentIndex = (currentIndex + 1) % 6 }, // cycles through all 6 images
        onBack = { currentIndex = if (currentIndex == 0) 5 else currentIndex - 1 },
        onGo = { inputIndex ->
            if (inputIndex in 1..6) {
                currentIndex = inputIndex - 1
            }
        },
        onTextInputChange = { textInput = it }
    )
}

@Composable
fun SlideshowContent(
    currentIndex: Int,
    textInput: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onGo: (Int) -> Unit,
    onTextInputChange: (String) -> Unit
) {
    val images = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6
    )

    val captions = listOf(
        stringResource(R.string.caption_1),
        stringResource(R.string.caption_2),
        stringResource(R.string.caption_3),
        stringResource(R.string.caption_4),
        stringResource(R.string.caption_5),
        stringResource(R.string.caption_6)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        )

        Text(
            text = captions[currentIndex],
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = textInput,
            onValueChange = onTextInputChange,
            label = { Text(stringResource(R.string.enter_picture_number)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Button(onClick = { onGo(textInput.toIntOrNull() ?: -1) }) {
            Text(stringResource(R.string.go_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = onBack) {
                Text(stringResource(R.string.back_button))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = onNext) {
                Text(stringResource(R.string.next_button))
            }
        }
    }
}

@Preview(name = "Interactive Mode", showBackground = true)
@Composable
fun PreviewSlideshowScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    var textInput by remember { mutableStateOf("") }

    SlideshowAppTheme {
        SlideshowContent(
            currentIndex = currentIndex,
            textInput = textInput,
            onNext = { currentIndex = (currentIndex + 1) % 6 },
            onBack = { currentIndex = if (currentIndex == 0) 5 else currentIndex - 1 },
            onGo = { inputIndex ->
                if (inputIndex in 1..6) {
                    currentIndex = inputIndex - 1
                }
            },
            onTextInputChange = { textInput = it }
        )
    }
}
