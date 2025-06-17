package com.app.gozi.food.presentation

import android.util.Log
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
import androidx.compose.material.icons.filled.Search
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
import com.app.gozi.food.domain.model.FoodItem
import com.app.gozi.food.presentation.viewmodel.FoodViewModel
import com.app.ui.resources.DrawableResources

@Composable
fun FoodScreen(
    onBackPressed: () -> Unit,
    onAddPressed: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FoodViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    LaunchedEffect(Unit) {
        viewModel.loadFoodItems()
    }
    val scrollProgress = remember {
        derivedStateOf {
            val maxScrollDistance = 250.dp.value - 60.dp.value // Sửa từ 80dp thành 60dp để khớp với navigation bar
            val currentScroll = listState.firstVisibleItemScrollOffset.toFloat()
            (currentScroll / (maxScrollDistance * 2)).coerceIn(0f, 1f) // Giảm từ 3 xuống 2 để animation nhanh hơn
        }
    }
    

    val headerHeightProgress by animateFloatAsState(
        targetValue = scrollProgress.value,
        animationSpec = tween(
//            durationMillis = 200,
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
        targetValue = if (headerHeightProgress > 0.7f) {
            Color.White
        } else {
            Color.White
        },
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
            )        }        // Scrollable Content - sẽ trượt lên trên header
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f), // Chỉ giữ zIndex, bỏ background toàn bộ LazyColumn
            contentPadding = PaddingValues(
                top = lerp(250.dp, 60.dp, headerHeightProgress), 
                bottom = 16.dp
            ),            verticalArrangement = Arrangement.spacedBy(0.dp) // Bỏ spacing để không có khoảng trống
        ) {
            // Item đầu tiên với background để che header khi cuộn lên  
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // Chiều cao để tạo vùng overlap
                        .background(MaterialTheme.colorScheme.background)
                )            }
            
            // Search bar
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { viewModel.searchFoodItems(it) },
                        placeholder = { Text("Tìm kiếm món ăn...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
              // Loading state
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            
            // Error state
            state.error?.let { error ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Lỗi",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(
                                    onClick = { 
                                        viewModel.clearError()
                                        viewModel.loadFoodItems() 
                                    }
                                ) {
                                    Text("Thử lại")
                                }
                            }
                        }
                    }
                }
            }
              // Food items
            items(state.foodItems) { foodItem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background) // Đảm bảo background cho mỗi item
                ) {
                    FoodItemCard(
                        foodItem = foodItem,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
          // Fixed Navigation Bar - luôn ở trên cùng
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f), // Đảm bảo navigation bar luôn ở trên cùng
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
                    text = "Ăn uống",
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
private fun FoodItemCard(
    foodItem: FoodItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title and price row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = foodItem.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${
                            foodItem.price?.toInt()
                                .toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,")}₫",
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
                            text = foodItem.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⭐",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = foodItem.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                Text(
                    text = foodItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Tags and preparation time
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (foodItem.isVegetarian) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF4CAF50)
                        ) {
                            Text(
                                text = "🌱 Chay",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    if (foodItem.isSpicy) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFF5722)
                        ) {
                            Text(
                                text = "🌶️ Cay",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = "⏱️ ${foodItem.preparationTime} phút",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                // Ingredients                if (foodItem.ingredients.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nguyên liệu: ${foodItem.ingredients.take(3).joinToString(", ")}${if (foodItem.ingredients.size > 3) "..." else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }

