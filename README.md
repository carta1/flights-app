# Flights app

This application is a basic airline app that shows all the airports in the world which the airline can travel to and more.

# Features

- Displays the detail information of the airports (id, latitude, longitude, name, city and countryId, and nearest airport in km)
- Highlight of the airport in the map
- Highligh of the furthest airports from each other
- The closest airport to the airport selected and its distance
- Setting here the user can convert to the desired distance unit (Kilometers or Miles)
- Localization support for three languages: English, Spanish, and Dutch
- Closest airport in ascending order to AMS 

# Structure of the app
- The app uses MVVM with SOLID architecture principles without a core module

# Major libraries and components used
- Google maps api, api key should be put inside the gradle.properties by adding this variable: GOOGLE_MAPS_API_KEY="AIzaSyBpgP8PaTYP1fEcFC-igdwjxkXJQSBa1PM"
this is done to keep the unique api key secure
- JetPack compose, new ui set of libraries for Android. No XML was used in this project
- Retrofit, OKHttp, moshi, and gson for webcalls and management of the json
- Hilt for dependency injection 
- Mockito, Junit, and Robolectric for unit test
- Jetpack DataStore for the app preferences (new way for Android to store preferences
