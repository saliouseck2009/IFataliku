package com.example.ifataliku.home.souvenirs

import IFatalikuTheme
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.NavigationDestination
import com.example.ifataliku.R
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
) {
    val context = LocalContext.current

    when(state) {
        is SouvenirState.Success -> {
            PageContent(data = data, onEvent = onEvent, viewModelEvent = viewModelEvent)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PageContent(
    data: SouvenirStateData,
    onEvent: (SouvenirUIEvent) -> Unit,
    viewModelEvent: Flow<SouvenirViewModelEvent>,
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
        state = SouvenirState.Success,
        data = SouvenirStateData(souvenirs = data),
        onEvent = {  },
        viewModelEvent = flow {  },

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
            state = SouvenirState.Success,
            data = SouvenirStateData(souvenirs = data),
            onEvent = {  },
            viewModelEvent = flow {  },

            )
    }

}