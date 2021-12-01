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

# App images
<p float="left">
<img src="https://user-images.githubusercontent.com/20260943/144272048-bb66cbad-0b82-43aa-b383-3b9819f87614.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144272101-13a0bdc5-8f18-40a2-a5b6-5aa0dce525b2.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144272122-83b79421-95b8-4c4c-9863-e0ad591c4b5a.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144272134-b9042ab0-1745-4597-9a16-d410b9dcf654.png" width="200" />
</p>
