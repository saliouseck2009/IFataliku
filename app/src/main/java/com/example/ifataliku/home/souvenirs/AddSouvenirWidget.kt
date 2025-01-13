package com.example.ifataliku.home.souvenirs

import IFatalikuTheme
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.R
import com.example.ifataliku.core.di.LocationUtils
import com.example.ifataliku.core.di.ObserveAsEvents
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.core.di.asColor
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.widgets.CircularIconView
import com.example.ifataliku.widgets.ColorItemWidget
import com.example.ifataliku.widgets.DatePickerWidget
import com.example.ifataliku.widgets.EmojiPicker
import com.example.ifataliku.widgets.ImagePickerView
import com.example.ifataliku.widgets.StaticMapView
import com.example.ifataliku.widgets.TimePickerWidget
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSouvenirWidget(
    onClose: () -> Unit,
    onSave: () -> Unit,
    onEvent: (SouvenirUIEvent) -> Unit,
    souvenir: Souvenir,
    modifier: Modifier = Modifier,
    viewModelEvent: Flow<AddSouvenirViewModelEvent>
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    ObserveAsEvents(flow = viewModelEvent) { event ->
        when (event) {
            is AddSouvenirViewModelEvent.RetrieveImageLocation -> {
                val location = LocationUtils.getImageLocation(context, event.uri)
                onEvent(SouvenirUIEvent.OnLocationSelected(lat = location.first, lng = location.second))
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
               showBottomSheet = false
            },

            sheetState = sheetState,
        ){
            BottomPagerView(
                souvenir = souvenir,
                onEvent = onEvent,
                selectedTab = selectedTab,
            )
        }
    }
    Surface{
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            var selectedImageUris by remember {
                mutableStateOf<List<Uri>>(emptyList())
            }

            val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
               // contract = ActivityResultContracts.PickMultipleVisualMedia(6),
                contract = ActivityResultContracts.GetMultipleContents(),
                onResult = { uris ->
                    onEvent(SouvenirUIEvent.OnImageSelected(uris))
                    selectedImageUris = uris
                }
            )
            Header(onClose = onClose, onSave = onSave)
            QuestionSection(
                emoji = souvenir.emoji,
                value = souvenir.title,
                onEmojiChosen = {
                    onEvent(SouvenirUIEvent.OnEmojiSelected(it))
                },
                onValueChange = {
                    onEvent(SouvenirUIEvent.OnTitleChanged(it))
                }
            )
            DescriptionSection(souvenir.description?:"",
                modifier = Modifier.padding(horizontal = 16.dp),
                onChanged = {
                onEvent(SouvenirUIEvent.OnDescriptionChanged(it))
            })

            DatePickerSection(
                selectedDate = souvenir.date,
                selectedColor = souvenir.color.color.asColor(),
                onValueChange = {
                    onEvent(SouvenirUIEvent.OnDateChanged(it))
                },
                onTimePicked = {
                    onEvent(SouvenirUIEvent.OnTimeChanged(it))
                },
                selectedTime = souvenir.time
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                TitledItemSection(
                    title = stringResource(R.string.category),
                    value = souvenir.category.emoji + " " + souvenir.category.title,
                    onClick = {
                        selectedTab = 0
                        showBottomSheet = true
                    },
                    modifier = Modifier.padding(8.dp)
                )
                TitledItemSection(
                    title = stringResource(R.string.color),
                    value =  souvenir.color.label,
                    onClick = {
                        selectedTab = 1
                        showBottomSheet = true
                    },
                    prefixValueWidget = {
                        ColorItemWidget(
                            color = souvenir.color.color.asColor(),
                            size = 15,
                            onClick ={},
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )
                TitledItemSection(
                    title = stringResource(R.string.mood),
                    value = souvenir.feeling.emoji + " " + souvenir.feeling.title,
                    onClick = {
                        selectedTab = 2
                        showBottomSheet = true
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            ImagePickerView(
                modifier = Modifier.padding(horizontal = 16.dp),
                pickPhotos = {
                    multiplePhotoPickerLauncher
                        .launch("image/*")
//                        .launch(
//                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                    )
                },
                images = selectedImageUris,
                color = souvenir.color.color.asColor()
            )
            Spacer(modifier = Modifier.height(16.dp))
            StaticMapView(
                coordinates = souvenir.position,
                color = souvenir.color.color.asColor(),
                onClick = {
                          onEvent(SouvenirUIEvent.OnFetchCurrentLocation)
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinkSection(souvenir.link?:"", onEvent, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun LinkSection(
    value: String,
    onEvent: (SouvenirUIEvent) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.link), style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        DefaultTextFieldView(
            value = value,
            placeHolder = stringResource(R.string.please_enter_a_valid_url)+".ex: https://www.google.com",
            onValueChange = {
                onEvent(SouvenirUIEvent.OnLinkChanged(it))
            },
            modifier = Modifier
        )
    }
}

@Composable
fun TimePickerView(){

    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // On button click, TimePicker is
        // displayed, user can select a time
        Button(onClick = { mTimePickerDialog.show() }, colors = ButtonDefaults.buttonColors(containerColor =
        Color(0XFF0F9D58))) {
            Text(text = "Open Time Picker", color = Color.White)
        }

        // Add a spacer of 100dp
        Spacer(modifier = Modifier.size(100.dp))

        // Display selected time
        Text(text = "Selected Time: ${mTime.value}", fontSize = 30.sp)
    }
}

@Composable
private fun TitledItemSection(
    title: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    prefixValueWidget: @Composable () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp, start = 8.dp)) {
            Text(
                title, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row {
                prefixValueWidget()
                Text(value, style = MaterialTheme.typography.bodyMedium, modifier = Modifier
                    )
            }
        }
    }
}

@Composable
private fun DescriptionSection(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultTextFieldView(value = value,
        placeHolder = stringResource(R.string.describe_it_in_detail),
        onValueChange = onChanged,
        modifier = modifier)
}




@Composable
fun IconButtonView(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Close,
){
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(15),
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
data class LabelledColor(val label: String, val color: String)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerSection(
    selectedColor: LabelledColor,
    onColorChanged: (LabelledColor) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.what_hue_defines_your_souvenir), style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
            val rows = 4
            val columns = 4
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                maxItemsInEachRow = rows
            ) {
                val itemModifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(70.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                repeat(rows * columns) {
                    val colorItem = AppData.colorItems[it]
                    Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =itemModifier
                ) {
                    ColorItemWidget(
                        color = Utils.getColorFromHexString(colorItem.color),
                        onClick = { onColorChanged(colorItem) },
                        isSelected = selectedColor.color == colorItem.color,
                        size = 20,
                        modifier = Modifier
                    )
                        Spacer(modifier = Modifier.height(4.dp))
                    Text(colorItem.label, style = MaterialTheme.typography.bodySmall,
                        textAlign =
                    TextAlign.Center)
                }
                }
            }

    }

}

@Composable
@Preview(showBackground = true)
fun GridItemSectionPreview() {
    GridItemSection(
        selectedItem = AppData.categories.first().title,
        onValueChanged = {},
        items = AppData.categories,
        modifier  = Modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GridItemSection(
    items: List<com.example.ifataliku.home.reflection.Category>,
    selectedItem: String,
    onValueChanged: (value: com.example.ifataliku.home.reflection.Category) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.fillMaxWidth()
        ) {
        Text(
            stringResource(R.string.which_category_your_souvenir_falls_into), style = MaterialTheme.typography
                .titleMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )

        val rows = 3
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            maxItemsInEachRow = rows,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(items.size) {
                val isSelected = items[it].title == selectedItem
                CategoryItemView(
                    title = items[it].title,
                    emoji = items[it].emoji,
                    onClick = { onValueChanged(items[it]) },
                    isSelected = isSelected,
                    modifier = Modifier
                        .weight(1f)
                        //.padding(bottom = if(it == items.size - 1) 32.dp else 0.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

        }

    }

}

@Composable
private fun FeelingPicker(
    selectedEmoji:Category,
    onValueChange: (Category) -> Unit,
    modifier: Modifier = Modifier
){
    val emojis  = AppData.emojis
    Column(
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.how_did_you_feel_when_taking_this_souvenir),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
            )
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
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmojiPickerItem(
    emoji: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val sizeScale by animateFloatAsState(if (isSelected) 1.3f else 1f, label = "size scale")


    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(15),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            modifier = modifier
                .graphicsLayer(
                scaleX = sizeScale,
                scaleY = sizeScale
            )
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.alpha(if (isSelected) 1f else 0.5f)
                ) {
                    Text(
                        text = emoji.emoji, style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = emoji.title, style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                if (isSelected) {
                    Surface {

                    }
                }
            }
        }
    }
}



@Composable
private fun DatePickerSection(
    selectedDate: String,
    selectedTime: String?,
    onValueChange: (String) -> Unit,
    onTimePicked: (String) -> Unit,
    selectedColor: Color,
    modifier: Modifier = Modifier
){

    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
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
            Text(text = "Date", color= MaterialTheme.colorScheme.outline,)
            Row {
                DatePickerWidget(
                    value = selectedDate,
                    onValueChange = onValueChange,
                    pattern = "yyyy-MM-dd"
                ) {
                    Text(
                        text = Utils.getFormattedDate(selectedDate),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = selectedColor,
                        modifier = Modifier
                            .clickable {
                                it()
                            }
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                TimePickerWidget(onTimePicked = onTimePicked, selectedTime = selectedTime, color =
                selectedColor)
            }

        }
    }
}

@Composable
private fun QuestionSection(
    emoji: String,
    value : String,
    onEmojiChosen: (String) -> Unit,
    onValueChange: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.what_would_you_like_to_remember),
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography
            .titleMedium)
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                onClick = { }) {
                EmojiPicker(
                    selectedEmoji = emoji,
                    onEmojiSelected = { emoji ->
                        onEmojiChosen(emoji)
                    },
                    size = 35,
                    modifier = Modifier
                        .size(55.dp)
                )
            }
            DefaultTextFieldView(value,
                stringResource(R.string.title_for_your_memory), onValueChange,Modifier.padding(start
            = 8.dp))


        }
    }
}

@Composable
private fun DefaultTextFieldView(value: String,placeHolder: String = "", onValueChange: (String) -> Unit, modifier:
Modifier = Modifier) {
    OutlinedTextField(
        shape = RoundedCornerShape(8.dp),
        value = value,
        placeholder = {
                      Text(text =placeHolder, color = MaterialTheme.colorScheme.outline )
        },
        onValueChange = onValueChange,

        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        ), modifier = modifier
            //.height(50.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun Header(
    onClose: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ){
        CircularIconView(onClose)
        Text(text = stringResource(R.string.nouveau_souvenir),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(1.dp, color = Color.Transparent, shape = CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
                .clickable(onClick = onSave)
        ) {
            Text(text = stringResource(R.string.ajouter),
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp))
        }
    }
}




@Composable
fun BottomPagerView(
    modifier: Modifier = Modifier,
    souvenir: Souvenir ,
    selectedTab: Int = 0,
    onEvent: (SouvenirUIEvent) -> Unit,
){

    val pagerState = rememberPagerState(pageCount = {
        3
    })
    LaunchedEffect(key1 = selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.49f)
            .padding(bottom = 16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        GridItemSection(
                            items = AppData.categories,
                            selectedItem = souvenir.category.title,
                            onValueChanged = {
                                onEvent(SouvenirUIEvent.OnCategorySelected(it))
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxHeight(),
                        )
                    }

                    1 -> {
                        ColorPickerSection(
                            selectedColor = souvenir.color,
                            onColorChanged = {
                                onEvent(SouvenirUIEvent.OnColorSelected(it))
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxHeight()
                        )
                    }

                    2 -> {
                        FeelingPicker(
                            selectedEmoji = souvenir.feeling,
                            onValueChange = {
                                onEvent(SouvenirUIEvent.OnFeelingSelected(it))
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxHeight()
                        )
                    }
                }

            }

        }
        PagerIndicator(
            pageCount = pagerState.pageCount,
            currentPageIndex = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }

}

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                ,
        ){
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical =  8.dp, horizontal = 12.dp)
                    ,
            ) {
                repeat(pageCount) { iteration ->
                    val color = if (currentPageIndex == iteration) Color.LightGray else Color.DarkGray
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItemView(
    emoji: String,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
){
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        modifier = modifier

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                emoji, style = MaterialTheme.typography.titleLarge,
            )
            Text(title, style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryItemViewPreview() {
    CategoryItemView(
        emoji = "🎉",
        title = "Celebration",
        onClick = {},
        isSelected = true
    )
}

@Preview(showBackground = true)
@Composable
fun BottomPagerViewPreview(){
    BottomPagerView(
        souvenir = souvenirs.first(),
        onEvent = {}
    )
}



@Composable
@Preview(showBackground = true)
fun AddSouvenirWidgetPreview() {
    IFatalikuTheme {
        AddSouvenirWidget(onClose = {},
            onSave = {}, onEvent = {}, souvenir = souvenirs.first(), viewModelEvent = flow {  }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddSouvenirWidgetDarkPreview() {
    IFatalikuTheme(darkTheme = true) {
        AddSouvenirWidget(onClose = {},
            onSave = {}, onEvent = {}, souvenir = souvenirs.first(), viewModelEvent = flow {  }
        )
    }
}

