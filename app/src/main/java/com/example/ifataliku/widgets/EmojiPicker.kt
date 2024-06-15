package com.example.ifataliku.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.core.di.capitalizeWords
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerBottomSheetUI
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerEmojiUI



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiPicker(
    size: Int = 30,
    selectedEmoji: String = "🎉",
    onEmojiSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var isModalBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    var searchText by remember {
        mutableStateOf("")
    }

    if (isModalBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RectangleShape,
            tonalElevation = 0.dp,
            onDismissRequest = {
                isModalBottomSheetVisible = false
                searchText = ""
            },
            dragHandle = null,
            windowInsets = WindowInsets(0),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                ComposeEmojiPickerBottomSheetUI(
                    searchBarColor = MaterialTheme.colorScheme.surface,
                    onEmojiClick = { emoji ->
                        isModalBottomSheetVisible = false
                        onEmojiSelected(emoji.character)
                    },
                    onEmojiLongClick = { emoji ->
                        Toast.makeText(
                            context,
                            emoji.unicodeName.capitalizeWords(),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                    searchText = searchText,
                    updateSearchText = { updatedSearchText ->
                        searchText = updatedSearchText
                    },
                )
            }
        }
    }

    Box(

        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            //.shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
           // .fillMaxSize()
    ) {
        ComposeEmojiPickerEmojiUI(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            emojiCharacter = selectedEmoji,
            onClick = {
                isModalBottomSheetVisible = true
            },
            fontSize = size.sp,
        )
    }
}