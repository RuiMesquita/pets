package com.example.pets.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.pets.presentation.ui.theme.customTypography


@Composable
fun TopBar(
    displayLeftButton: Boolean = true,
    displayRightButton: Boolean = true,
    leftButtonOnClickAction: () -> Unit = {},
    rightButtonOnClickAction: () -> Unit = {},
    rightIcon: Int? = null,
    leftIcon: Int? = null,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left icon
        if (displayLeftButton) {
            Box(
                modifier = Modifier
                    .height(46.dp)
                    .width(46.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clickable { leftButtonOnClickAction() },
                contentAlignment = Alignment.Center
            ) {
                if (leftIcon != null) {
                    Image(
                        imageVector = ImageVector.vectorResource(leftIcon),
                        contentDescription = "Arrow back"
                    )
                }
            }
        }
        else {
            EmptyPlaceHolderButton()
        }


        // Middle text
        Text(
            text = title,
            style = customTypography.titleLarge
        )

        // Right icon
        if (displayRightButton) {
            Box(
                modifier = Modifier
                    .height(46.dp)
                    .width(46.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clickable { rightButtonOnClickAction() },
                contentAlignment = Alignment.Center,
            ) {
                if (rightIcon != null) {
                    Image(
                        imageVector = ImageVector.vectorResource(rightIcon),
                        contentDescription = "Notification"
                    )
                }

            }
        }
        else {
            EmptyPlaceHolderButton()
        }
    }
}


@Composable
fun EmptyPlaceHolderButton() {
    Box(modifier = Modifier.height(46.dp).width(46.dp))
}