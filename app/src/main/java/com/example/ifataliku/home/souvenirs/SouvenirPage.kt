package com.example.ifataliku.home.souvenirs

import IFatalikuTheme
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.NavigationDestination
import com.example.ifataliku.R
import com.example.ifataliku.core.di.LocationUtils.getImageLocation
import com.example.ifataliku.core.di.ObserveAsEvents
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.widgets.ErrorWidget
import com.example.ifataliku.widgets.LoadingPage
import com.example.ifataliku.widgets.SouvenirListItemView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object SouvenirDestination : NavigationDestination {
    override val route: String = "souvenir"
    override val titleRes: Int = R.string.ma_journ_e
}

@Composable
fun SouvenirPage(
    viewModelEvent: Flow<SouvenirViewModelEvent>,
    state: SouvenirState,
    data: SouvenirStateData,
    onEvent: (SouvenirUIEvent) -> Unit,
    addSouvenirViewModelEvent: Flow<AddSouvenirViewModelEvent>,
) {
    val context = LocalContext.current

    when(state) {
        is SouvenirState.Success -> {
            PageContent(data = data, onEvent = onEvent, viewModelEvent = viewModelEvent, addSouvenirViewModelEvent = addSouvenirViewModelEvent)
        }
        is SouvenirState.Error -> {
             ErrorWidget(
                 text = state.message.asString(context),
                 onRetry = { onEvent(SouvenirUIEvent.InitPageData) },
                 onCancel = {  }
             )
        }
        is SouvenirState.Loading -> {
             LoadingPage()
        }
    }

}

@Composable
fun SelectAndRetrieveImageLocation() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            RetrieveImageLocation(uri = it, context = context)
        }
    }
}
@Composable
fun RetrieveImageLocation(uri: Uri?, context: Context) {
    // Mutable state to hold the extracted location
    var location by remember { mutableStateOf<Pair<Double?, Double?>>(Pair(null,null)) }

    // Button to trigger location extraction
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            uri?.let {
                // Retrieve location metadata from the image
                location = getImageLocation(context, uri)
                println("location: $location")
            }
        }) {
            Text("Retrieve Image Location")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the extracted location
        Text(
            text = location.toString() ?: "No location data found",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PageContent(
    data: SouvenirStateData,
    onEvent: (SouvenirUIEvent) -> Unit,
    viewModelEvent: Flow<SouvenirViewModelEvent>,
    addSouvenirViewModelEvent: Flow<AddSouvenirViewModelEvent>,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val context = LocalContext.current
    ObserveAsEvents(flow = viewModelEvent) { event ->
        when (event) {
            is SouvenirViewModelEvent.OpenAddSouvenir -> {
                showBottomSheet = true
            }
            is SouvenirViewModelEvent.ShowMessage -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val topAppBarTextSize = 28.sp

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                    title = {
                        Text(
                            text = stringResource(id = SouvenirDestination.titleRes),
                            fontSize = topAppBarTextSize,
                        )
                    },
                    navigationIcon = {},
                actions = {
                        IconButton(onClick = {
                            onEvent(SouvenirUIEvent.OpenAddSouvenir)
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                }
                )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
//            SelectAndRetrieveImageLocation()
//            Spacer(modifier = Modifier.height(16.dp))
            data.souvenirs
                .forEach { (month, souvenirs) ->
                    Spacer(modifier = Modifier.height(16.dp))
                    SouvenirListItemView(items = souvenirs, dateTitle = month)
                    Spacer(modifier = Modifier.height(16.dp))
            }


        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxHeight(0.97f),
        ) {
            Box(
                Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 10.dp)
            ) {
                AddSouvenirWidget(
                    souvenir = data.souvenir!!,
                    onClose = { showBottomSheet = false },
                    onSave = {
                        onEvent(SouvenirUIEvent.OnValidateNewSouvenir)
                    },
                    viewModelEvent = addSouvenirViewModelEvent,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SouvenirPagePreview() {
    val data = souvenirs
        .groupBy { Utils.getDateFromString(it.date).month.name }
        .map { (month, souvenirs) ->
            Pair(month, souvenirs)
        }
    SouvenirPage(
        viewModelEvent = flow {  },
        state = SouvenirState.Success,
        data = SouvenirStateData(souvenirs = data),
        onEvent = {  },
        addSouvenirViewModelEvent = flow {  },

    )
}

@Composable
@Preview(showBackground = true)
fun SouvenirPageDarkPreview() {
    IFatalikuTheme(darkTheme = true) {
        val data = souvenirs
            .groupBy { Utils.getDateFromString(it.date).month.name }
            .map { (month, souvenirs) ->
                Pair(month, souvenirs)
            }
        SouvenirPage(
            viewModelEvent = flow {  },
            state = SouvenirState.Success,
            data = SouvenirStateData(souvenirs = data),
            onEvent = {  },
            addSouvenirViewModelEvent = flow {  },

            )
    }

}