# Project Title
Sports App with MVVM and Clean Architecture
## Getting Started
Minimun version of android SDK 21 - Install all dependencies - Android studio version 3.6.1
    //Library list and more
    
    implementation 'com.google.android.material:material:1.1.0'
    implementation 'androidx.coordinatorlayout:coordinatorlayout:1.1.0'

    //ViewModel and LiveData
    implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'

    //Recyclerview
    implementation 'androidx.recyclerview:recyclerview:1.1.0-alpha05'
    implementation 'androidx.cardview:cardview:1.0.0'

    // Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.5.0"
    implementation "com.squareup.retrofit2:converter-gson:2.5.0"
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.7.2'

    // Gson
    implementation "com.google.code.gson:gson:2.8.5"

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'


    //Rx
    implementation 'io.reactivex.rxjava2:rxjava:2.2.7'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    
    ### And coding style tests
Examples:

Api connection with returns a Single (Observable rx-java) 

```
@GET("lookupteam.php")
    fun getTeamDetails(@Query("id") id: Int):Single<LookUpTeamResponse> 
```
Example of class repository for fetching a list of soccer teams

```
class TeamDetailsRepository
    (private val apiService : TeamApiService) {

    fun fetchTeamDetails(teamId: Int): Single<List<Team>> {
              return apiService.getTeamDetails(teamId)
                    .flatMap { Single.just(it.teams) }
                    .subscribeOn(Schedulers.io())
    }

}
```
Screenshots

![home_screen](https://user-images.githubusercontent.com/28877279/83355683-aaed8900-a326-11ea-9857-3312d736bc20.jpg)



## Deployment
This project was built under the MVVM presentation pattern and using clean architecture parameters, such as presentation layers, repositories, etc.

## Versioning
this is the start version and we will continue to improve the use of patterns and the scalability of the code

## Authors

* **Julian Andres Cuartas** - *Initial work* - [HomeWork](https://github.com/jcuartas1)
