package com.app.gozi.home.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ui.resources.DrawableResources

// Data class for features
data class FeatureItem(
    val title: String,
    val imageRes: Int
)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onFoodFeatureClick: () -> Unit = {},
    onSportFeatureClick: () -> Unit = {}
) {
    val showMessageBox by viewModel.showMessageBox.collectAsState()
    val animatedText by viewModel.animatedText.collectAsState()    // Feature list data
    val features = remember {
        listOf(
            FeatureItem("Ăn uống", DrawableResources.Features.foodAndDragon),
            FeatureItem("Thể thao", DrawableResources.Features.batminton)
        )
    }

    // Animation cho floating button (zoom in/out)
    val infiniteTransition = rememberInfiniteTransition(label = "buttonPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleAnimation"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {        // Main content with scrollable feature list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)        ) {
            items(features) { feature ->                FeatureCard(
                    feature = feature,
                    onClick = {
                        if (feature.title == "Ăn uống") {
                            onFoodFeatureClick()
                        } else if (feature.title == "Thể thao") {
                            onSportFeatureClick()
                        }
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = showMessageBox,
            enter = slideInHorizontally(
                initialOffsetX = { it / 2 },
                animationSpec = tween(400, easing = EaseOutBack, delayMillis = 100)
            ) + scaleIn(
                initialScale = 0.3f,
                transformOrigin = androidx.compose.ui.graphics.TransformOrigin(1f, 1f),
                animationSpec = tween(400, easing = EaseOutBack, delayMillis = 100)
            ) + fadeIn(animationSpec = tween(300, delayMillis = 50)),
            exit = slideOutHorizontally(
                targetOffsetX = { it / 2 },
                animationSpec = tween(300)
            ) + scaleOut(
                targetScale = 0.3f,
                transformOrigin = androidx.compose.ui.graphics.TransformOrigin(1f, 1f),
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300)),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 88.dp, bottom = 25.dp)
        ) {
            Card(
                modifier = Modifier
                    .widthIn(min = 180.dp, max = 280.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ), border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = animatedText,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { viewModel.onFloatingButtonClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(64.dp)
                .scale(scale),
            shape = CircleShape,
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ai_ask),
                contentDescription = "AI Assistant",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun FeatureCard(
    feature: FeatureItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = feature.imageRes),
                contentDescription = feature.title, modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Shadow overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            // Title with arrow icon overlay
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Navigate",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
