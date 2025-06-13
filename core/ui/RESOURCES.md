# Core UI Resources

Thư mục này chứa các tài nguyên UI được chia sẻ giữa các module trong ứng dụng.

## Drawable Resources

### Cấu trúc thư mục
```
core/ui/src/main/res/drawable/
├── food_and_dragon.png    # Ảnh cho feature ăn uống
└── batminton.png          # Ảnh cho feature thể thao
```

### Cách sử dụng

Thay vì sử dụng trực tiếp `R.drawable`, hãy sử dụng `DrawableResources` object:

```kotlin
import com.app.ui.resources.DrawableResources

// Sử dụng trong code
val foodImage = DrawableResources.Features.foodAndDragon
val sportsImage = DrawableResources.Features.batminton
```

### Lợi ích

1. **Tái sử dụng**: Các module khác có thể dễ dàng sử dụng cùng một tài nguyên
2. **Quản lý tập trung**: Tất cả tài nguyên được quản lý ở một nơi
3. **Type Safety**: Sử dụng object thay vì magic strings
4. **Dễ bảo trì**: Thay đổi tên resource chỉ cần sửa ở một nơi

### Thêm tài nguyên mới

1. Thêm file ảnh vào `core/ui/src/main/res/drawable/`
2. Cập nhật `DrawableResources.kt` để thêm reference mới
3. Sử dụng trong các module khác thông qua dependency `implementation(project(":core:ui"))`
