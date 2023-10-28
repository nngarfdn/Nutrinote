# Nutrinote Gama

Nutrinote is a digital canteen application designed for monitoring nutrition. This application is implemented at the UGM Nutrition canteen. The development of this application is supported by a backend team that utilizes a REST API. For the Android version, Kotlin is used, along with Android Architecture Components such as LiveData, MVVM, Paging, Repository, Injection (Koin), Idling Resources, Scanner, LeakCanary, and Logger.

![Screenshot](https://gcdnb.pbrd.co/images/KxpOnDKByaB3.png?o=1)

## Download
You can download the latest APK from [here](https://drive.google.com/file/d/1x5JUGEO3HQKNHsDDmsaMHFHTE-p1qvZP/view).

## Tech Stack & Open-Source Libraries

- **Minimum SDK level**: 21
- **Language**: Kotlin

### Asynchronous Programming
- Coroutines: Used for managing asynchronous operations.
- Flow: Handling asynchronous data streams.

### Jetpack Components
- Lifecycle: Observes Android lifecycles and manages UI states based on lifecycle changes.
- ViewModel: Manages UI-related data, making it lifecycle-aware to ensure data survives configuration changes.
- ViewBinding: Binds UI components to data sources efficiently and type-safely, reducing the need for manual UI updates.
- Koin: Facilitates dependency injection, making it easier to manage dependencies in your app.

### Material Design
- Material Components: A library for material design components, including ripple animations and CardView.

### Image Loading
- Glide and GlidePalette: Glide is used to load images from the network, and GlidePalette allows extracting and working with colors from loaded images.

### Data Storage
- DataStore: Manages and stores app-specific data in a key-value format.

### Firebase Services
- Firebase BOM (Bill of Materials): A set of dependencies that makes it easier to work with Firebase services.
- Firebase Analytics: For tracking user engagement and app usage.
- Firebase Crashlytics: Provides real-time crash reporting for your app.

### UI Components
- ViewPager2: Creates swipeable screens.
- DotsIndicator: Displays indicators for ViewPager2.
- Balloon: A library for creating tooltips and pop-up messages.
- Android-Simple-Tooltip: Shows simple and customizable tooltips.
- Code Scanner: Utilizes the "com.github.yuriy-budiyev:code-scanner" library for code scanning functionality.

## MAD Score

![MAD Score](https://gcdnb.pbrd.co/images/0IlhTDWNIeGf.png?o=1)

