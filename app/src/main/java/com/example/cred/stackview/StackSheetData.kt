package com.example.cred.stackview

import androidx.compose.runtime.Stable
import java.util.UUID

@Stable
data class StackSheetData(
    val sheetState: StackSheetState = StackSheetState.Collapsed,
    val frameId: String = UUID.randomUUID().toString()
)
