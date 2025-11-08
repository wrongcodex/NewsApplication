package com.example.newsapplication

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permission: String,
    isPermanentlyDeclined: Boolean,
    onDismiss: ()-> Unit,
    onOkClick: ()-> Unit,
    onGoToAppSettings: ()-> Unit
) {
    AlertDialog(
        confirmButton = {},
        onDismissRequest = TODO(),
        modifier = TODO(),
        dismissButton = TODO(),
        icon = TODO(),
        title = TODO(),
        text = TODO(),
        shape = TODO(),
        containerColor = TODO(),
        iconContentColor = TODO(),
        titleContentColor = TODO(),
        textContentColor = TODO(),
        tonalElevation = TODO(),
        properties = TODO(),
    )
}