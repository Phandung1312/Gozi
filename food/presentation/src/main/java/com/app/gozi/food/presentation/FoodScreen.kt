package com.app.gozi.food.presentation

import androidx.compose.animation.core.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ui.resources.DrawableResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(
    onBackPressed: () -> Unit,
    onAddPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val scrollProgress = remember {
        derivedStateOf {
            val maxScrollDistance = 250.dp.value - 80.dp.value
            val currentScroll = listState.firstVisibleItemScrollOffset.toFloat()
            (currentScroll / (maxScrollDistance * 3)).coerceIn(0f, 1f)
        }
    }
    

    val headerHeightProgress by animateFloatAsState(
        targetValue = scrollProgress.value,
        animationSpec = tween(
            durationMillis = 200,
            easing = EaseInOutCubic
        ),
        label = "headerHeightAnimation"
    )
    

    val backgroundAlpha by animateFloatAsState(
        targetValue = 1f - scrollProgress.value,
        animationSpec = tween(
            durationMillis = 200,
            easing = EaseInOutCubic
        ),
        label = "backgroundAlphaAnimation"
    )
    

    val navigationBackgroundColor by animateColorAsState(
        targetValue = if (headerHeightProgress > 0.8f) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = EaseInOutCubic
        ),
        label = "navigationBackgroundColorAnimation"
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {        // Main content với navigation offset
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(lerp(250.dp, 64.dp, headerHeightProgress))
                ) {

                    Image(
                        painter = painterResource(id = DrawableResources.Features.foodAndDragon),
                        contentDescription = "Food Background",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(backgroundAlpha),
                        contentScale = ContentScale.Crop
                    )
                    

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(backgroundAlpha)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.2f),
                                        Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            )
                    )
                }
            }

            items(20) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = if (index == 0) 16.dp else 0.dp, bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Món ăn ${index + 1}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Mô tả món ăn ngon và hấp dẫn. Đây là nội dung tạm thời để kiểm tra scroll behavior của header.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
        

        TopAppBar(
            title = {
                Text(
                    text = "Ăn uống",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onAddPressed
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = navigationBackgroundColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun lerp(start: androidx.compose.ui.unit.Dp,
                 stop: androidx.compose.ui.unit.Dp,
                 fraction: Float): androidx.compose.ui.unit.Dp {
    return start + (stop - start) * fraction
}
