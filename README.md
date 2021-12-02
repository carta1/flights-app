# Flights app

This application is a basic airline app that shows all the airports in the world that airlines can travel to directly from Schiphol and more.

# Features

- Displays the detail information of the airports (id, latitude, longitude, name, city, countryId, and nearest airport in km or mi)
- Mark all the airports in the map
- Highlight of the furthest airports from each other
- Ability to select an airport on the map
- The closest airport to the airport selected and its distance
- Setting where the user can convert to the desired distance unit (Kilometers or Miles)
- Localization support for three languages: English, Spanish, and Dutch
- Closest airports in ascending order (by distance) to AMS 

# Structure of the app
- The app uses MVVM with SOLID architecture principles without a core module

# Major libraries and components used
- Google maps api, api key should be put inside the gradle.properties by adding this variable: GOOGLE_MAPS_API_KEY="ADD-YOUR-API-KEY"
this is done to keep the unique api key secure
- JetPack compose, new UI set of libraries for Android. No XML was used in this project
- Retrofit, OKHttp, moshi, and gson for webcalls and management of the json
- Hilt for dependency injection 
- Mockito, Junit, and Robolectric for unit test
- Jetpack DataStore for the app preferences (new way for Android to store preferences 

# App images
<p float="left">
<img src="https://user-images.githubusercontent.com/20260943/144471691-8ea77e65-2731-4630-a24d-a5018710cc9f.png" width="200" />  
<img src="https://user-images.githubusercontent.com/20260943/144272101-13a0bdc5-8f18-40a2-a5b6-5aa0dce525b2.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144471722-b51fcdf6-08a0-423b-81e5-c09ef112492f.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144272101-13a0bdc5-8f18-40a2-a5b6-5aa0dce525b2.png" width="200" />
<img src="https://user-images.githubusercontent.com/20260943/144471894-8919ea44-2a9b-4138-9fc2-9d06e934854a.png" width="200" />
</p>
