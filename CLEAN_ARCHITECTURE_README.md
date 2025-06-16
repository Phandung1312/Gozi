# Gozi App - Clean Architecture Implementation

## Tổng quan
Dự án Gozi đã được tổ chức theo Clean Architecture pattern với khả năng linh hoạt chuyển đổi giữa mock data và real data sources cho cả Food và Sport modules.

## Cấu trúc Clean Architecture

### 1. Domain Layer (Business Logic)
- **Models**: Định nghĩa entities cho ứng dụng
- **Repository Interfaces**: Contracts cho data access
- **Use Cases**: Business logic cụ thể

#### Food Module
```
food/domain/
├── model/
│   └── FoodItem.kt
├── repository/
│   └── FoodRepository.kt
└── usecase/
    ├── GetAllFoodItemsUseCase.kt
    └── SearchFoodItemsUseCase.kt
```

#### Sport Module
```
sport/domain/
├── model/
│   └── SportItem.kt
├── repository/
│   └── SportRepository.kt
└── usecase/
    ├── GetAllSportItemsUseCase.kt
    └── SearchSportItemsUseCase.kt
```

### 2. Data Layer (Data Access)
- **Repository Implementations**: Concrete implementations
- **Data Sources**: Mock và Remote data sources
- **Dependency Injection**: Configuration modules

#### Food Module
```
food/data/
├── local/
│   └── MockFoodData.kt
├── repository/
│   ├── MockFoodRepositoryImpl.kt
│   └── RemoteFoodRepositoryImpl.kt
├── di/
│   └── FoodDataModule.kt
└── config/
    └── FoodRepositoryConfigModule.kt
```

#### Sport Module
```
sport/data/
├── local/
│   └── MockSportData.kt
├── repository/
│   ├── MockSportRepositoryImpl.kt
│   └── RemoteSportRepositoryImpl.kt
├── di/
│   └── SportDataModule.kt
└── config/
    └── SportRepositoryConfigModule.kt
```

### 3. Presentation Layer (UI)
- **ViewModels**: State management với Flows
- **Composables**: UI components
- **Navigation**: Screen navigation setup

#### Food Module
```
food/presentation/
├── viewmodel/
│   └── FoodViewModel.kt
├── FoodScreen.kt
└── navigation/
    └── FoodNavigation.kt
```

#### Sport Module
```
sport/presentation/
├── viewmodel/
│   └── SportViewModel.kt
├── SportScreen.kt
└── navigation/
    └── SportNavigation.kt
```

## Cách chuyển đổi giữa Mock và Real Data

### Food Module
Thay đổi flag trong `FoodRepositoryConfigModule.kt`:
```kotlin
private const val USE_MOCK_DATA = true // false để sử dụng real data
```

### Sport Module
Thay đổi flag trong `SportRepositoryConfigModule.kt`:
```kotlin
private const val USE_MOCK_DATA = true // false để sử dụng real data
```

## Dependency Injection với Hilt

### Qualifiers
- `@MockFoodRepository` / `@MockSportRepository`: Mock implementations
- `@RemoteFoodRepository` / `@RemoteSportRepository`: Remote implementations

### Modules
- `FoodDataModule` / `SportDataModule`: Bind repository implementations
- `FoodRepositoryConfigModule` / `SportRepositoryConfigModule`: Provide main repository based on configuration

## Features Implementation

### Food Module
- ✅ 10 món ăn Việt Nam với thông tin chi tiết
- ✅ Categories: Món chính, Đồ ăn nhanh, Khai vị, etc.
- ✅ Tìm kiếm theo tên, mô tả, nguyên liệu
- ✅ Rating, giá cả, thời gian chuẩn bị
- ✅ Tags: Chay, Cay
- ✅ Loading states và error handling

### Sport Module
- ✅ 20 hoạt động thể thao đa dạng
- ✅ Categories: Vợt, Đồng đội, Cá nhân, Thể hình, etc.
- ✅ Difficulty levels: Dễ, Trung bình, Khó
- ✅ Calories, thời gian, thiết bị cần thiết
- ✅ Indoor/Outdoor, Team/Individual indicators
- ✅ Benefits và equipment information

## UI Implementation

### Shared Features
- Header animation với scroll behavior
- Navigation bar với title và actions
- Card-based layout với Material Design 3
- Loading indicators và error handling
- Responsive design

### Food Screen
- Background image: Food & Dragon theme
- Price display với formatting Việt Nam
- Category tags và rating stars
- Ingredient và preparation time info

### Sport Screen
- Background image: Badminton theme
- Calorie display
- Difficulty color coding
- Indoor/Outdoor và Team sport badges
- Equipment và benefits information

## Build Configuration

### Dependencies
- Hilt for Dependency Injection
- Compose for UI
- Coroutines & Flow for reactive programming
- Material Design 3

### Module Structure
```
app/
├── build.gradle.kts (includes all presentation + data modules)
├── food/
│   ├── domain/
│   ├── data/
│   └── presentation/
└── sport/
    ├── domain/
    ├── data/
    └── presentation/
```

## Cách sử dụng

1. **Development với Mock Data**: Set `USE_MOCK_DATA = true` để development nhanh
2. **Production với Real API**: Set `USE_MOCK_DATA = false` và implement API services
3. **Testing**: Có thể test riêng từng layer nhờ dependency injection
4. **Scaling**: Dễ dàng thêm modules mới theo pattern này

## Lợi ích của Clean Architecture

1. **Separation of Concerns**: Mỗi layer có trách nhiệm riêng biệt
2. **Testability**: Dễ unit test từng layer
3. **Flexibility**: Có thể thay đổi data source mà không ảnh hưởng UI
4. **Maintainability**: Code được tổ chức rõ ràng, dễ maintain
5. **Scalability**: Dễ dàng thêm features mới

## Next Steps

1. Implement real API services
2. Add caching layer với Room database
3. Implement offline support
4. Add more advanced search filters
5. Implement detail screens cho từng item
