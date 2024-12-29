package com.example.ifataliku.widgets

import IFatalikuTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ifataliku.R
import com.example.ifataliku.core.di.UiText


@Composable
@Preview
fun PermissionDialogPreview() {
    IFatalikuTheme {
        PermissionDialog(
            permissionTextProvider = MediaLocationPermissionTextProvider(),
            isPermanentlyDeclined = false,
            onDismiss = {},
            onOkClick = {},
            onGoToAppSettingsClick = {}
        )
    }

}

@Composable
@Preview
fun PermissionDialogPreview1() {
    IFatalikuTheme(darkTheme = true) {
        PermissionDialog(
            permissionTextProvider = MediaLocationPermissionTextProvider(),
            isPermanentlyDeclined = false,
            onDismiss = {},
            onOkClick = {},
            onGoToAppSettingsClick = {}
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Surface {

            Column(

            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {


                    Text(
                        text = "Permission required",
                        color = MaterialTheme.colorScheme.onSurface
                            .copy(alpha = 0.6f),
                        style =
                        MaterialTheme.typography
                            .titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = permissionTextProvider.getDescription(
                            isPermanentlyDeclined = isPermanentlyDeclined
                        ).asString(context),
                        color = MaterialTheme.colorScheme.onSurface
                            .copy(alpha = 0.6f),
                    )
                }
                HorizontalDivider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        }
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): UiText
}

class MediaLocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean,): UiText {
        return if(isPermanentlyDeclined) {
            UiText.StringResource(R.string
                .it_seems_you_permanently_declined_media_location_permission_you_can_go_to_the_app_settings_to_grant_it)
        } else {
           UiText.StringResource(R.string
               .this_app_needs_access_to_your_media_location_to_know_where_you_are)
        }
    }
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean,): UiText {
        return if(isPermanentlyDeclined) {
            UiText.StringResource(R.string.it_seems_you_permanently_declined_location_permission_you_can_go_to_the_app_settings_to_grant_it)
        } else {
            UiText.StringResource(R.string.this_app_needs_access_to_your_location_to_know_where_you_are)
        }
    }
}
