package com.example.cred.stackview

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.cred.R

@Composable
fun StackViewComp(
    modifier: Modifier = Modifier,
    state: StackViewState = rememberStackViewState(),
    heightBetweenSheet: Dp = 50.dp,
    collapsedHeight: Dp = 70.dp,
    bottomSheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
    content: @Composable (Int,StackSheetState) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        state.states.forEachIndexed { index, stackSheetData ->
            key(stackSheetData.frameId) {
                var sheetState by remember {
                    mutableStateOf<StackSheetState>(StackSheetState.Nothing)
                }
                LaunchedEffect(key1 = stackSheetData.sheetState, block = {
                    sheetState = stackSheetData.sheetState
                })
                val transition = updateTransition(
                    targetState = sheetState,
                    label = "StackViewSheet"
                )
                val height = transition.animateDp(
                    label = "StackSheetHeightAnim"
                ) {
                    when (it) {
                        StackSheetState.Expanded -> {
                            calculateHeight(
                                index = index,
                                maxHeight = maxHeight,
                                heightBetweenSheet = heightBetweenSheet
                            )
                        }

                        StackSheetState.Collapsed -> {
                            collapsedHeight
                        }

                        StackSheetState.Nothing -> {
                            0.dp
                        }
                    }
                }
                StackBottomSheet(
                    modifier = Modifier,
                    index = index,
                    stackSheetData = stackSheetData,
                    onIconClick = {
                        state.changeState(index)
                    },
                    content = content,
                    height = { height.value },
                    bottomSheetShape = bottomSheetShape
                )
            }
        }
    }
}

internal fun calculateHeight(index: Int, maxHeight: Dp, heightBetweenSheet: Dp): Dp {
    return maxHeight.minus(heightBetweenSheet.times(index))
}

@Composable
fun StackBottomSheet(
    modifier: Modifier,
    index: Int,
    bottomSheetShape: RoundedCornerShape,
    stackSheetData: StackSheetData,
    height: () -> Dp, // Deferred state read
    onIconClick: () -> Unit,
    content: @Composable (Int, StackSheetState) -> Unit
) {
    val animatedBackDrop = animateColorAsState(
        targetValue = if (stackSheetData.sheetState == StackSheetState.Expanded && index != 0) {
            Color.Black.copy(alpha = 0.3f)
        } else {
            Color.Transparent
        }
    )
    Box(modifier = modifier
        .fillMaxSize()
        .background(animatedBackDrop.value, shape = bottomSheetShape)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .requiredHeight(height())
                .clip(shape = bottomSheetShape)
                .zIndex(index.toFloat())
                .then(
                    if (stackSheetData.sheetState == StackSheetState.Collapsed) {
                        Modifier.clickable(onClick = onIconClick)
                    } else {
                        Modifier
                    }
                )
        ) {
            if (stackSheetData.sheetState == StackSheetState.Expanded) {
                IconButton(
                    onClick = onIconClick, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(48.dp)
                        .zIndex(50f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_keyboard_arrow_down_24),
                        contentDescription = "KeyBoardArrowDown",
                        tint = Color.White
                    )
                }
            }
            content(index,stackSheetData.sheetState)
        }
    }
}
