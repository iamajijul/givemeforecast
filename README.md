# GiveMeForecast

This Application mainly use for two purpose : 
1. To check the weather of particular city in the world as user desire.
2. To chek the weather forecast for use current location

The app follows the MVVM architecture with the repository pattern, alongside Dagger for DI.

#### Folder structure

There are 4 main folders: di, models, network, ui, utils.
* di: Contain Dagger 2 related files
* models: Data classes use for the network call response
* network: Retrofit network related classes present in this folder
* ui: All views related classes present in this folder. The app follows MVVM architecture coding pattern 
for this project.
* utils: Contains utilities, base classes, extension function etc classes. 

### Design, libraries and other stuff applied

* Data binding
* Google Material Components
* ViewModel and LiveData
* Coroutines
* Dagger 2 for dependency injection
* Retrofit
* MVVM architecture + Repository pattern
* Junit4 
* Espresso
* Navigation Architecture Component
* Constraint Layout

Whole project written in kotlin language.



### Testing!

I written one class unit test, "BaseRepository.kt". 

***Code Coverage : I already did required to activate code coverage functionality. To get code coverage
follow below step :
1. Goto right side bar on Android Studio, you will get "Gradle" option there. Select that option.
2. Under gradle, you will get "app" folder. Double click on that folder or select that folder and
 press gradle icon above of folder hierarchy.
3. You will get two field, one of them already filled with project name,
on second field type "createCodeCoverageReport" and press "OK". Your project should start building.
4. After Successful Build, you can find the related report inside 
app->build->reports->coverage->debug->index.html
Open index.html file on your browser, you will get code coverage report.



### Permission!

1. Internet permission
2. Location Permission

### Note!

"http://openweathermap.org"
not allow to search multiple city for weather, no API is there for that. so user can search only one city 
for weather. 