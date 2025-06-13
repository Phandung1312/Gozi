package com.app.ui.resources

import com.app.ui.R

/**
 * Object để quản lý các drawable resources được chia sẻ
 * giữa các module khác nhau trong ứng dụng
 */
object DrawableResources {
    
    /**
     * Feature images - Ảnh cho các tính năng chính
     */
    object Features {
        val foodAndDragon = R.drawable.food_and_dragon
        val batminton = R.drawable.batminton
    }
    
    /**
     * Có thể mở rộng thêm các category khác như:
     * object Icons { ... }
     * object Backgrounds { ... }
     * object Illustrations { ... }
     */
}
