# TmdbCleanMVI-Android

**Technology Stack:**
- Kotlin Programming Language
- MVI Architecture Pattern
- ViewModel
- Coroutine
- Flow
- Navigation Graph
- Toothpick Dependency Injection
- ViewBinding
- Retrofit REST + OkHttp
- GSON Serialization
- Picasso Image Loader
- All layouts are implemented using ConstraintLayout
- Gradle build flavors
- Proguard
- Embedded Youtube Android Player

**Caution:**
**All the api calls are switching to IO dispatchers in DataSource class**

To run Code Coverage (JaCoCo):
1. Open Terminal then move to "root_project" directory.
2. type "./gradlew codeCoverModules allDebugCodeCoverage" (enter), wait until finish executing.

The report file will be located in "root_project/build/reports/jacoco/allDebugCoverage/html/index.html", open it using browser.

**Caution:**
**Later I will add more unit tests to increase the code coverage value.**
