# CryptoApp

Android-приложение для просмотра курсов криптовалют. Загружает данные с **CoinGecko Demo API**, сохраняет их в локальную базу данных Room и отображает в списке с возможностью открыть подробную информацию о монете.

Поддерживает портретную и ландшафтную ориентации (Master-Detail), автоматическую синхронизацию данных в фоновом режиме и обработку ошибок.

## Скриншоты

### Портретная ориентация

![Список криптовалют — портрет](screenshots/screenshot_portrait.png)

### Ландшафтная ориентация

![Список и детали — ландшафт](screenshots/screenshot_landscape.png)

## Возможности

- Список топ-50 криптовалют с ценой, символом и логотипом.
- Детальный экран монеты: цена, символ, логотип и время обновления.
- Master-Detail: в портретной ориентации — список на весь экран и детали поверх; в ландшафтной — список и детали рядом.
- Кнопка «Закрыть» на экране деталей и обработка системной кнопки «Назад».
- Кеширование данных в Room — список сохраняется между запусками приложения.
- Автоматическая загрузка данных при старте приложения.
- Фоновая синхронизация криптовалют с помощью WorkManager.
- Внедрение зависимостей через Hilt.
- `HiltWorker` для внедрения зависимостей в фоновые задачи WorkManager.
- Обработка ошибок в ViewModel и Worker с помощью `try/catch`.
- ViewBinding вместо `findViewById` и Kotlin synthetics.
- `ListAdapter` + `DiffUtil` для эффективного обновления списка.
- Разделение приложения на слои Data, Domain и Presentation.

## Технологический стек

- **Язык:** Kotlin 2.1.10
- **Сборка:** AGP 8.8.0, Gradle 8.10.2
- **SDK:** compileSdk 35, minSdk 21, targetSdk 35
- **Архитектура:** Clean Architecture, MVVM
- **Dependency Injection:** Hilt
- **Сеть:** Retrofit 2, Gson
- **База данных:** Room 2.6.1, KSP
- **Асинхронность:** Kotlin Coroutines, suspend-функции, viewModelScope, lifecycleScope
- **Фоновая работа:** WorkManager, CoroutineWorker, HiltWorker
- **UI:** ViewBinding, RecyclerView, ListAdapter, DiffUtil, Picasso, Material Components
- **API:** CoinGecko Demo API (`coins/markets`)

## Архитектура проекта

```text
com.example.cryptoapp
│
├── api
│   ├── ApiService.kt
│   └── dto
│       └── CoinGeckoDto.kt
│
├── di
│   ├── DatabaseModule.kt       (предоставление Room и DAO)
│   ├── NetworkModule.kt        (Retrofit, Gson, ApiService)
│   └── RepositoryModule.kt     (связывание Repository)
│
├── data
│   ├── database
│   │   ├── AppDatabase.kt
│   │   ├── CoinPriceInfoDao.kt
│   │   └── CoinPriceInfoDbModel.kt
│   │
│   ├── mapper
│   │   └── CoinPriceInfoMapper.kt
│   │
│   └── repository
│       └── CoinRepositoryImpl.kt
│
├── domain
│   ├── CoinPriceInfo.kt
│   ├── repository
│   │   └── CoinRepository.kt
│   └── usecase
│       ├── GetPriceListUseCase.kt
│       ├── GetDetailInfoUseCase.kt
│       └── LoadDataUseCase.kt
│
├── presentation
│   ├── MainActivity.kt
│   ├── CoinViewModel.kt
│   ├── CoinInfoAdapter.kt
│   ├── CoinPriceListFragment.kt
│   └── CoinDetailFragment.kt
│
├── work
│   └── CoinSyncWorker.kt       (фоновая синхронизация)
│
└── MyApp.kt                   (инициализация Hilt)
```

## Dependency Injection — Hilt

В приложении используется Hilt для внедрения зависимостей.

Основные модули:

- **DatabaseModule** — создание Room Database и предоставление DAO.
- **NetworkModule** — создание Gson, Retrofit и ApiService.
- **RepositoryModule** — связывание интерфейса CoinRepository с реализацией CoinRepositoryImpl.

Зависимости внедряются через конструкторы с `@Inject`.

`@HiltAndroidApp` используется для инициализации контейнера зависимостей приложения, а `@AndroidEntryPoint` — для Android-компонентов, участвующих во внедрении зависимостей.

## Фоновая синхронизация — WorkManager

Для фонового обновления данных используется WorkManager.

- `CoinSyncWorker` отвечает за выполнение синхронизации.
- `CoroutineWorker` позволяет выполнять сетевые операции с использованием корутин.
- `@HiltWorker` обеспечивает внедрение зависимостей в Worker.
- `HiltWorkerFactory` используется для создания Worker с зависимостями.
- Обработка исключений предотвращает неконтролируемое завершение фоновой задачи.

## Обработка ошибок

В `CoinViewModel` и `CoinSyncWorker` предусмотрена обработка исключений через `try/catch`.

Это позволяет обрабатывать ошибки сетевых запросов и фоновой синхронизации без аварийного завершения приложения.

## Сборка и запуск

1. Откройте проект в Android Studio.
2. Дождитесь завершения Gradle Sync.
3. Настройте API-ключ CoinGecko.
4. Запустите приложение на эмуляторе или Android-устройстве.

### API-ключ CoinGecko

Приложение использует CoinGecko Demo API.

Ключ хранится локально и не добавляется непосредственно в исходный код.

1. Зарегистрируйтесь на [CoinGecko](https://www.coingecko.com/) и получите Demo API-ключ в Developer Dashboard.
2. В корне проекта откройте `local.properties` и добавьте:

```properties
COINGECKO_API_KEY=ваш_ключ
```

3. Убедитесь, что `local.properties` находится в `.gitignore`.
4. В `app/build.gradle` ключ передаётся через `buildConfigField`.
5. `ApiService` использует `BuildConfig.COINGECKO_API_KEY` для авторизации запросов.

**Важно:** не публикуйте API-ключ в GitHub и не добавляйте `local.properties` в коммит.

## История рефакторинга

Проект изначально использовал CryptoCompare API, RxJava и Activity для отображения списка и деталей.

В ходе рефакторинга выполнены следующие изменения:

- **Сеть:** CryptoCompare заменён на CoinGecko Demo API, RxJava заменена Kotlin Coroutines.
- **База данных:** внедрена Room с отдельной Entity-моделью `CoinPriceInfoDbModel`.
- **Архитектура:** реализована Clean Architecture с разделением на Data, Domain и Presentation.
- **Бизнес-логика:** добавлены UseCase и Mapper.
- **UI:** реализован Master-Detail интерфейс, ViewBinding, ListAdapter и DiffUtil.
- **Dependency Injection:** добавлен Hilt для управления зависимостями.
- **Фоновая работа:** добавлен WorkManager с поддержкой HiltWorker.
- **Обработка ошибок:** добавлена обработка исключений в ViewModel и Worker.
- **API-ключ:** перенесён из исходного кода в `local.properties` с передачей через BuildConfig.

## Лицензия

Исходный код основан на учебных материалах курса.

Рефакторинг под Clean Architecture, замена API на CoinGecko Demo API, переход на Kotlin Coroutines, ViewBinding, Hilt и WorkManager, а также доработка UI выполнены Вячеславом Дроновым.

Проект предназначен для обучения и демонстрации практических навыков Android-разработки.