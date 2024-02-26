package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SuccessAlertDialog(show: Boolean, onConfirmPressed: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = onConfirmPressed) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Submitted Successfully")
        }, text = {
            Text(text = "Your form has been successfully submitted.")
        })
    }
}

@Composable
fun ErrorAlertDialog(show: Boolean, onConfirmPressed: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = onConfirmPressed) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Error Submitting Form")
        }, text = {
            Text(text = "An error occurred while submitting your form. Please try again.")
        })
    }
}

@Composable
fun ErrorAlertDialog(show: Boolean, text: String, onConfirmPressed: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = onConfirmPressed) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Error")
        }, text = {
            Text(text = text)
        })
    }
}
