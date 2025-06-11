package com.app.gozi.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import com.app.gozi.presentation.navigation.BottomNavItem
import com.app.ui.theme.PrimaryColor

@Composable
fun AnimatedBottomNavigation(
    items: List<BottomNavItem>,
    selectedItem: String,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        return
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in items.indices) {
                val item = items[index]
                BottomNavItem(
                    item = item,
                    isSelected = selectedItem == item.route,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}

@Composable
private fun BottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("BottomNavItem", "Rendering item: ${item.title}, selected: $isSelected")

    var clickAnimationPlaying by remember { mutableStateOf(false) }

    // Animation values
    val animationSpec = remember {
        spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    }
    val scale by animateFloatAsState(
        targetValue = if (clickAnimationPlaying) 1.2f else 1f,
        animationSpec = animationSpec,
        label = "scale",
        finishedListener = {
            if (clickAnimationPlaying) {
                clickAnimationPlaying = false
            }
        }
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.6f
        ),
        animationSpec = tween(200),
        label = "iconColor"
    )

    Box(
        modifier = modifier
            .height(60.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                clickAnimationPlaying = true
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                colorFilter = ColorFilter.tint(iconColor)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.title,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = iconColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = if (isSelected) 1.05f else 1f
                    scaleY = if (isSelected) 1.05f else 1f
                }
            )
        }
    }
}



