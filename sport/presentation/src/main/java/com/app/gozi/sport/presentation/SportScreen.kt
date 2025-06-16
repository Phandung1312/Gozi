package com.app.gozi.sport.presentation

import androidx.compose.animation.core.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.presentation.viewmodel.SportViewModel
import com.app.ui.resources.DrawableResources

@Composable
fun SportScreen(
    onBackPressed: () -> Unit,
    onAddPressed: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: SportViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    LaunchedEffect(Unit) {
        viewModel.loadSportItems()
    }
    
    val scrollProgress = remember {
        derivedStateOf {
            val maxScrollDistance = 250.dp.value - 60.dp.value
            val currentScroll = listState.firstVisibleItemScrollOffset.toFloat()
            (currentScroll / (maxScrollDistance * 2)).coerceIn(0f, 1f)
        }
    }
    
    val headerHeightProgress by animateFloatAsState(
        targetValue = scrollProgress.value,
        animationSpec = tween(
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

    val navigationContentColor by animateColorAsState(
        targetValue = Color.White,
        animationSpec = tween(
            durationMillis = 200,
            easing = EaseInOutCubic
        ),
        label = "navigationContentColorAnimation"
    )
    
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Fixed Header Background - không cuộn cùng với content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(lerp(250.dp, 60.dp, headerHeightProgress))
                .zIndex(0f)
        ) {
            Image(
                painter = painterResource(id = DrawableResources.Features.batminton),
                contentDescription = "Sport Background",
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
        
        // Scrollable Content - sẽ trượt lên trên header
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentPadding = PaddingValues(
                top = lerp(250.dp, 60.dp, headerHeightProgress), 
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Item đầu tiên với background để che header khi cuộn lên  
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
            }
              // Show loading indicator
            if (state.isLoading && state.sportItems.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            
            // Show error message
            state.error?.let { error ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = error,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            
            items(state.sportItems) { sportItem ->
                SportItemCard(
                    sportItem = sportItem,
                    modifier = Modifier.padding(
                        start = 16.dp, 
                        end = 16.dp, 
                        top = 16.dp, 
                        bottom = 16.dp
                    )
                )
            }
        }
        
        // Fixed Navigation Bar - luôn ở trên cùng
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f),
            color = Color.Black.copy(alpha = 0.3f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Navigation Icon
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = navigationContentColor
                    )
                }
                
                // Title
                Text(
                    text = "Thể thao",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = navigationContentColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                
                // Action Icon
                IconButton(
                    onClick = onAddPressed,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = navigationContentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun lerp(start: androidx.compose.ui.unit.Dp,
                 stop: androidx.compose.ui.unit.Dp,
                 fraction: Float): androidx.compose.ui.unit.Dp {
    return start + (stop - start) * fraction
}

@Composable
private fun SportItemCard(
    sportItem: SportItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title and calories row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = sportItem.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${sportItem.calories} cal",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Category and rating row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = sportItem.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    
                    if (sportItem.rating > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⭐",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = sportItem.rating.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                Text(
                    text = sportItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Difficulty and duration row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Difficulty tag
                    val difficultyColor = when (sportItem.difficulty) {
                        "Dễ" -> Color(0xFF4CAF50)
                        "Trung bình" -> Color(0xFFFF9800)
                        "Khó" -> Color(0xFFFF5722)
                        else -> MaterialTheme.colorScheme.primary
                    }
                    
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = difficultyColor
                    ) {
                        Text(
                            text = sportItem.difficulty,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    
                    // Indoor/Outdoor tag
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (sportItem.isIndoor) Color(0xFF2196F3) else Color(0xFF8BC34A)
                    ) {
                        Text(
                            text = if (sportItem.isIndoor) "🏠 Trong nhà" else "🌳 Ngoài trời",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    
                    // Team sport tag
                    if (sportItem.isTeamSport) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF9C27B0)
                        ) {
                            Text(
                                text = "👥 Đội nhóm",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = "⏱️ ${sportItem.duration} phút",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                // Equipment and benefits
                if (sportItem.equipment.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Dụng cụ: ${sportItem.equipment.take(3).joinToString(", ")}${if (sportItem.equipment.size > 3) "..." else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                if (sportItem.benefits.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Lợi ích: ${sportItem.benefits.take(2).joinToString(", ")}${if (sportItem.benefits.size > 2) "..." else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
