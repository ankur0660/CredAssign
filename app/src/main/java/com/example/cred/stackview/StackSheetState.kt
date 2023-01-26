package com.example.cred.stackview

sealed interface StackSheetState {
    object Expanded : StackSheetState
    object Collapsed : StackSheetState
    object Nothing : StackSheetState
}
