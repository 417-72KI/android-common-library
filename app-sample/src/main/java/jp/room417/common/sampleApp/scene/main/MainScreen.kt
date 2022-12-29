package jp.room417.common.sampleApp.scene.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MainScreen(
    viewModel: IMainViewModel,
    onAuthAction: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        viewModel.error.value?.let {
            ErrorAlert(it, onDismiss = viewModel::onClearError)
        }
        val isLoading by viewModel.isLoading
        if (isLoading) {
            Dialog(onDismissRequest = {}) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (viewModel.hasAccessToken) {
                val focusManager = LocalFocusManager.current
                Greeting("Twitter")
                val text by viewModel.text
                TextField(
                    value = text,
                    onValueChange = viewModel::onChangeText,
                    modifier = Modifier.padding(20.dp),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )
                Button(
                    onClick = { viewModel.post(text) },
                    enabled = text.isNotBlank()
                ) {
                    Text("Post")
                }
                val activity = LocalContext.current as? Activity
                Button(onClick = {
                    viewModel.resetAuth()
                    activity?.finishAffinity()
                }) {
                    Text("Reset authentication")
                }
            } else {
                Greeting("World")
                Button(onClick = onAuthAction) {
                    Text("Auth twitter")
                }
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
private fun ErrorAlert(e: Exception, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(e.localizedMessage ?: "")
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//private fun DefaultPreview() {
//    AndroidcommonlibraryTheme {
//        MainScreen(FakeMainViewModel())
//    }
//}
