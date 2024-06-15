package com.example.ifataliku.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.R

@Composable
fun ErrorWidget(
    text: String,
    onRetry: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    retryButtonText : String = stringResource(R.string.r_essayer),
    icon: ImageVector = Icons.Outlined.Close,
    iconBgColor: Color = MaterialTheme.colorScheme.error,
    iconButton: ImageVector? = null,
) {
    ActionResultWidget(
        text = text,
        title = title,
        icon = icon,
        iconBgColor = iconBgColor,
        modifier = modifier,

        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (iconButton == null) {
                ActionButton(
                    onClick = onRetry,
                    text = retryButtonText,
                    color = MaterialTheme.colorScheme.primary,
                    horizontalPadding = 32.dp
                )
            } else {
                ButtonRightIcon(
                    onClick = onRetry,
                    text = retryButtonText,
                    icon = iconButton,
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            }

            TextButton(onClick = onCancel) {
                Text(
                    stringResource(id = R.string.retour),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight(600),
                    )
                )
            }
        }
    }
}

@Composable
fun ActionResultWidget(
    text: String,
    modifier: Modifier = Modifier,
    title: String = "",
    icon: ImageVector = Icons.Filled.Check,
    iconBgColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                Surface(
                    shape = CircleShape,
                    color = iconBgColor,
                    contentColor = iconColor,
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                        )
                    }
                }
                if (title.isNotBlank()) {
                    Spacer(modifier = Modifier.padding(bottom = 15.dp))
                    Text(
                        title,
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                Text(
                    text,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                content()
            }
        }
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.valider),
    horizontalPadding: Dp = 0.dp,
    shape: Int = 7,
    color: Color = MaterialTheme.colorScheme.secondary,
    enabled: Boolean = true,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
        ),
        shape = RoundedCornerShape(size = shape.dp),

        ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
            ),
            modifier = modifier.padding(horizontal = horizontalPadding)
        )
    }
}

@Composable
fun ButtonRightIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = stringResource(R.string.se_connecter).uppercase(),
    icon: ImageVector = Icons.Filled.QrCode,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.clickable { onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(containerColor)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.padding(horizontal = 24.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    color = contentColor
                )
            )
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            Icon(
                icon,
                contentDescription = "Arrow left",
                tint = contentColor,
            )


        }
    }
}