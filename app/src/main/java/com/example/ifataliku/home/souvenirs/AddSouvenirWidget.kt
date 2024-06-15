package com.example.ifataliku.home.souvenirs

import IFatalikuTheme
import NoRippleTheme
import android.net.Uri
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.InsertLink
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ifataliku.R
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.TitleEmoji
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.home.reflection.categories
import com.example.ifataliku.widgets.ColorItemWidget
import com.example.ifataliku.widgets.CustomDropDownMenu
import com.example.ifataliku.widgets.DatePickerWidget
import com.example.ifataliku.widgets.EmojiPicker

@Composable
fun AddSouvenirWidget(
    onClose: () -> Unit ,
    onSave: () -> Unit ,
    onEvent: (SouvenirUIEvent) -> Unit ,
    souvenir: Souvenir,
    modifier: Modifier = Modifier) {
    Surface(

    ) {
        Column(

            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            var expandState = remember { mutableStateOf(false) }

            var selectedImageUris by remember {
                mutableStateOf<List<Uri>>(emptyList())
            }

            val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(),
                onResult = { uris ->
                    onEvent(SouvenirUIEvent.OnImageSelected(uris))
                    selectedImageUris = uris
                }
            )
            Header(onClose = onClose, onSave = onSave, choosedColor = souvenir.color)
            QuestionSection(
                emoji = souvenir.emoji,
                value = souvenir.title,
                onEmojiChoosed = {
                    onEvent(SouvenirUIEvent.OnEmojiSelected(it))
                },
                onValueChange = {
                    onEvent(SouvenirUIEvent.OnTitleChanged(it))
                }
            )

            DatePickerSection(
                choosedDate = souvenir.date,
                selectedColor = souvenir.color,
                onValueChange = {
                    onEvent(SouvenirUIEvent.OnDateChanged(it))
                }
            )
            CustomDropDownMenu(
                title = stringResource(R.string.category),
                items = categories.map { "${it.emoji} ${it.title}" },
                expanded = expandState.value,
                currentValue = souvenir.category.emoji + " " + souvenir.category.title,
                onValueChanged = { value ->
                    onEvent(SouvenirUIEvent.OnCategorySelected(value))

                },
                onExpanded = { value ->
                    expandState.value = value
                },
                modifier = modifier.padding(horizontal = 8.dp)
            )
            ColorPickerSection(
                selectedColor = souvenir.color,
                onColorChanged = {
                    onEvent(SouvenirUIEvent.OnColorSelected(it))
                },
                modifier = modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            FeelingPicker(
                selectedEmoji = souvenir.feeling,
                onValueChange = {
                    onEvent(SouvenirUIEvent.OnFeelingSelected(it))
                },
                selectedColor = souvenir.color,
                modifier = modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            AttachmentsSection(
                modifier = modifier.padding(horizontal = 16.dp),
                pickPhotos = {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ImagePreviewSection(
                imageUris = selectedImageUris,
                onSelectImages = {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                selectedColor = Utils.getColorFromHexString(souvenir.color),
                modifier = modifier
            )


        }
    }
}

@Composable
fun ImagePreviewSection(
    modifier: Modifier = Modifier,
    imageUris: List<Uri>,
    selectedColor: Color,
    onSelectImages: () -> Unit
) {
    Column {
       if(imageUris.isNotEmpty()) Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Images", style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text("Edit", style = MaterialTheme.typography.bodyMedium,
                color = selectedColor,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { onSelectImages() }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(16.dp)
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            imageUris.forEach { uri ->
                Card(
                    // modifier = Modifier.padding(8.dp),
                ) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(150.dp, 150.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Composable
fun AttachmentsSection(
    modifier: Modifier = Modifier,
    pickPhotos: () -> Unit ,
){
    Column(
        modifier = modifier
    ) {
        Text("Attachments", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(bottom = 16.dp, end = 8.dp)
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            IconedButton(
                icon = Icons.Filled.PhotoCamera,
                text = "Photos",
                onClick = pickPhotos
            )
            IconedButton(
                icon = Icons.Filled.MyLocation,
                text = "Location",
                onClick = {}
            )
            IconedButton(
                icon = Icons.Filled.InsertLink,
                text = "Link",
                onClick = {}
            )
        }
    }
}

@Composable
fun IconedButton(
    icon: ImageVector = Icons.Filled.Close,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(15),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Transparent,
//        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ColorPickerSection(
    selectedColor: String,
    onColorChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var colorItems = remember{ listOf<String>(
        "f72585",
        "E65C19",
        "b08968",
        "c9ada7",
        "81c3d7",
        "8DB600",
        "9f86c0",
        "2f6690",
        "898121",
        "B2BEB5",
        "76ABDF",
        "D0F0C0",
        "F5DEB3",
        "87CEEB"
    )}


    Column(modifier = modifier) {
        Text("Color", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(bottom = 16.dp, end = 8.dp)
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            colorItems.forEach { colorItem ->
                ColorItemWidget(
                    color = Utils.getColorFromHexString(colorItem),
                    isSelected = colorItem == selectedColor,
                    onClick ={ onColorChanged(colorItem)},
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }

}

@Composable
private fun FeelingPicker(
    selectedEmoji: TitleEmoji,
    onValueChange: (TitleEmoji) -> Unit,
    selectedColor: String,
    modifier: Modifier = Modifier
){
    var emojis  = remember{ listOf<TitleEmoji>(
        TitleEmoji("😩", "Terrible"),
        TitleEmoji("☹️️", "Bad"),
        TitleEmoji("😐", "Okay"),
        TitleEmoji("🙂", "Good"),
        TitleEmoji("😄", "Awesome"),
    )}
    Column(
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.how_do_you_feel), style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
            )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            emojis.forEach { emoji ->
                EmojiPickerItem(
                    emoji = emoji,
                    isSelected = emoji == selectedEmoji,
                    onClick = {onValueChange(emoji)},
                    color = Utils.getColorFromHexString(selectedColor),
                )
            }
        }
    }
}



@Composable
private fun EmojiPickerItem(
    emoji: TitleEmoji,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color, 
    modifier: Modifier = Modifier
){
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(15),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = emoji.emoji, style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = emoji.title, style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                    AnimatedVisibility(isSelected,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> -fullHeight },
                            animationSpec = tween(durationMillis = 500)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { fullHeight -> -fullHeight },
                            animationSpec = tween(durationMillis = 500)
                        )
                        ) {
                    Icon(
                        Icons.Filled.FiberManualRecord,
                        contentDescription = "plain circle",
                        tint = color, modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePickerSection(
    choosedDate: String,
    onValueChange: (String) -> Unit,
    selectedColor: String,
    modifier: Modifier = Modifier
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Date")
            DatePickerWidget(
                value = choosedDate,
                onValueChange = onValueChange,
                pattern = "yyyy-MM-dd"
            ) {
                Text(
                    text = choosedDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Utils.getColorFromHexString(selectedColor),
                    modifier = Modifier
                        .clickable {
                            it()
                        }
                )
            }
        }
    }
}

@Composable
private fun QuestionSection(
    emoji: String,
    value : String,
    onEmojiChoosed: (String) -> Unit,
    onValueChange: (String) -> Unit,

) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp , start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.what_would_you_like_to_remember), style = MaterialTheme.typography
            .titleMedium)
        Spacer(Modifier.height(12.dp))
        Row {
            Surface(
                shape = RoundedCornerShape(8.dp),
                onClick = { /*TODO*/ }) {
                EmojiPicker(
                    selectedEmoji = emoji,
                    onEmojiSelected = { emoji ->
                        onEmojiChoosed(emoji)
                    },
                    size = 30,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
                OutlinedTextField(
                    shape = RoundedCornerShape(8.dp),
                    value = value,
                    onValueChange = onValueChange,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    ,modifier = Modifier
                        .padding(start = 8.dp)
                        .height(50.dp)
                        .fillMaxWidth())


        }
    }
}

@Composable
private fun Header(
    onClose: () -> Unit ,
    onSave: () -> Unit ,
    choosedColor: String,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, color = Color.Transparent, shape = CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )

        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Add Souvenir")
            }
        }
        Text(text = stringResource(R.string.nouveau_souvenir),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(1.dp, color = Color.Transparent, shape = CircleShape)
                .background(
                    Utils
                        .getColorFromHexString(choosedColor)
                        .copy(alpha = 0.3f), shape = CircleShape
                )
                .clickable(onClick = onSave)
        ) {
            Text(text = stringResource(R.string.ajouter),
                color = Utils.getColorFromHexString(choosedColor),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddSouvenirWidgetPreview() {
    IFatalikuTheme {
        AddSouvenirWidget(souvenir = souvenirs.first(),
            onClose = {}, onSave = {}, onEvent = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddSouvenirWidgetDarkPreview() {
    IFatalikuTheme(darkTheme = true) {
        AddSouvenirWidget(souvenir = souvenirs.first(),
            onClose = {}, onSave = {}, onEvent = {}
        )
    }
}