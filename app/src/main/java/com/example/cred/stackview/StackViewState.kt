package com.example.cred.stackview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cred.removeSlice
import java.lang.RuntimeException

@Stable
class StackViewState(
    private val maxSlot: Int
) {
    var states by mutableStateOf<List<StackSheetData>>(listOf())
        private set
    init {
        states = buildList {
            repeat(2) {
                if (it == 0) {
                    add(StackSheetData(sheetState = StackSheetState.Expanded))
                } else {
                    add(StackSheetData(sheetState = StackSheetState.Collapsed))
                }
            }
        }
    }
    fun changeState(index: Int) {
        // 1 -> if (expanded) collapse as well as I have to remove the index -> maximum else index expand and insert 1 item as collapsed
        if (index in 1..states.size.coerceAtMost(maxSlot.minus(1))) {
            val newStates = states.toMutableList()
            val existing = newStates[index]
            if (existing.sheetState == StackSheetState.Expanded) {
                newStates[index] = existing.copy(sheetState = StackSheetState.Collapsed)
                if (index < states.size - 1) {
                    removeSlice(list = newStates, from = index + 1, end = states.size)
                }
                states = newStates
            } else {
                newStates[index] = existing.copy(sheetState = StackSheetState.Expanded)
                if (index < maxSlot.minus(1)) {
                    newStates.add(StackSheetData(StackSheetState.Collapsed))
                }
                states = newStates
            }
        }
    }
}

@Composable
fun rememberStackViewState(maxSlot: Int = 4): StackViewState {
    if (maxSlot !in 2..4) throw RuntimeException("Max slot should be minimum 2 to maximum 4")
    return remember {
        StackViewState(maxSlot = maxSlot)
    }
}
