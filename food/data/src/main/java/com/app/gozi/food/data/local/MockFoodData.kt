package com.app.gozi.food.data.local

import com.app.gozi.food.domain.model.FoodItem

object MockFoodData {
    val mockFoodItems = listOf(
        FoodItem(
            id = "1",
            name = "Phở Bò",
            description = "Món phở bò truyền thống Hà Nội với nước dùng đậm đà, bánh phở mềm và thịt bò tươi ngon.",
            imageUrl = null,
            category = "Món chính",
            price = 65000.0,
            rating = 4.8f,
            preparationTime = 15,
            ingredients = listOf("Bánh phở", "Thịt bò", "Hành lá", "Ngò gai", "Giá đỗ"),
            isVegetarian = false,
            isSpicy = false
        ),
        FoodItem(
            id = "2", 
            name = "Bánh Mì Thịt Nướng",
            description = "Bánh mì giòn rụm với thịt nướng thơm lừng, rau cải và nước sốt đặc biệt.",
            imageUrl = null,
            category = "Đồ ăn nhanh",
            price = 25000.0,
            rating = 4.5f,
            preparationTime = 10,
            ingredients = listOf("Bánh mì", "Thịt nướng", "Rau cải", "Cà chua", "Nước sốt"),
            isVegetarian = false,
            isSpicy = true
        ),
        FoodItem(
            id = "3",
            name = "Gỏi Cuốn Tôm",
            description = "Gỏi cuốn tươi mát với tôm, rau sống và bánh tráng, chấm cùng nước mắm pha.",
            imageUrl = null,
            category = "Khai vị",
            price = 35000.0,
            rating = 4.3f,
            preparationTime = 5,
            ingredients = listOf("Bánh tráng", "Tôm", "Rau sống", "Bún", "Nước mắm pha"),
            isVegetarian = false,
            isSpicy = false
        ),
        FoodItem(
            id = "4",
            name = "Chả Cá Lã Vọng",
            description = "Món chả cá đặc sản Hà Nội với cá được ướp nghệ, nướng thơm và ăn kèm bún, rau thơm.",
            imageUrl = null,
            category = "Món chính",
            price = 120000.0,
            rating = 4.7f,
            preparationTime = 20,
            ingredients = listOf("Cá", "Nghệ", "Bún", "Rau thơm", "Tỏi phi"),
            isVegetarian = false,
            isSpicy = false
        ),
        FoodItem(
            id = "5",
            name = "Đậu Hũ Sốt Cà Chua",
            description = "Món chay ngon miệng với đậu hũ non mềm mịn sốt cà chua chua ngọt.",
            imageUrl = null,
            category = "Món chay",
            price = 45000.0,
            rating = 4.2f,
            preparationTime = 12,
            ingredients = listOf("Đậu hũ", "Cà chua", "Hành tây", "Tỏi", "Gia vị"),
            isVegetarian = true,
            isSpicy = false
        ),
        FoodItem(
            id = "6",
            name = "Bún Bò Huế",
            description = "Món bún đặc sản miền Trung với nước dùng cay nồng, thịt bò và chả cua.",
            imageUrl = null,
            category = "Món chính",
            price = 70000.0,
            rating = 4.6f,
            preparationTime = 18,
            ingredients = listOf("Bún", "Thịt bò", "Chả cua", "Rau thơm", "Tôm chua"),
            isVegetarian = false,
            isSpicy = true
        ),
        FoodItem(
            id = "7",
            name = "Cơm Tấm Sườn",
            description = "Cơm tấm sài gòn truyền thống với sườn nướng, chả trứng và nước mắm chấm.",
            imageUrl = null,
            category = "Món chính", 
            price = 55000.0,
            rating = 4.4f,
            preparationTime = 15,
            ingredients = listOf("Cơm tấm", "Sườn nướng", "Chả trứng", "Đồ chua", "Nước mắm chấm"),
            isVegetarian = false,
            isSpicy = false
        ),
        FoodItem(
            id = "8",
            name = "Bánh Xèo",
            description = "Bánh xèo giòn tan với tôm, thịt, giá đỗ, ăn kèm rau sống và nước chấm.",
            imageUrl = null,
            category = "Món chính",
            price = 60000.0,
            rating = 4.5f,
            preparationTime = 20,
            ingredients = listOf("Bột bánh xèo", "Tôm", "Thịt ba chỉ", "Giá đỗ", "Rau sống"),
            isVegetarian = false,
            isSpicy = false
        ),
        FoodItem(
            id = "9",
            name = "Chè Ba Màu",
            description = "Món tráng miệng mát lạnh với đậu xanh, đậu đỏ, thạch và nước cốt dừa.",
            imageUrl = null,
            category = "Tráng miệng",
            price = 20000.0,
            rating = 4.1f,
            preparationTime = 5,
            ingredients = listOf("Đậu xanh", "Đậu đỏ", "Thạch", "Nước cốt dừa", "Đá bào"),
            isVegetarian = true,
            isSpicy = false
        ),
        FoodItem(
            id = "10",
            name = "Nem Nướng Nha Trang",
            description = "Nem nướng đặc sản Nha Trang thơm ngon, ăn kèm bánh tráng và rau sống.",
            imageUrl = null,
            category = "Khai vị",
            price = 80000.0,
            rating = 4.6f,
            preparationTime = 25,
            ingredients = listOf("Thịt heo", "Tôm", "Bánh tráng", "Rau sống", "Nước chấm"),
            isVegetarian = false,
            isSpicy = true
        )
    )
}
