# Trivia-Android-App
​
​
# Enhancements from Version 1.0
 
## 1. User Interface
The user interface has improved with some cool designs and colors that will enhance user affinity.

The interface is very user-friendly.

 

## 2. Save the highest score
Since each time a user will come and will achieve a greater score than the previous one then we need to save that score as the highest score.

Here we can use a database to store that score but I chose to use Share Preferences which is used to store small data in key-value pairs.

 This highest score should be saved in onPause activity because there may be a chance when our activity suddenly crashed, unfortunately, switched-off, etc then we may lose our data. So better to use life cycle activity for this.

 

 Shared Preferences

If you have a relatively small collection of key values that you'd like to save, you should use the SharedPreferences APIs. A SharedPreferences object points to a file containing key-value pairs and provides simple methods to read and write them. Each file is managed by the framework and can be private or shared.

This page shows you how to use the SharedPreferences APIs to store and retrieve simple values.

 

Get a handle on shared preferences
You can create a new shared preference file or access an existing one by calling one of these methods:

getSharedPreferences() — Use this if you need multiple shared preference files identified by name, which you specify with the first parameter. You can call this from any in your app.
getPreferences() — Use this from an Activity if you need to use only one shared preference file for the activity. Because this retrieves a default shared preference file that belongs to the activity, you don't need to supply a name.
For example, the following code accesses the shared preferences file that's identified by the resource string R.string.preference_file_key and opens it using the private mode so the file is accessible by only your app:

More

 

Context context = getActivity();
SharedPreferences sharedPref = context.getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

 

## 3. Current Score
The current score will vanish when you will terminate the app.

There may be a chance when accidentally your application crashed or terminated un-intentionally and your score is still not saved then there will be a loss of the data.

For this, I have used the activity life cycle and used onResume() to save the current score.

 

## 4. Saving the state of the app
Each time the user will come and start from the beginning. Isn't it good? Of course not, because we have to store that state of the app.

Let's say you were at question 100 last time and accidentally your android is dead due to low battery. Then no need to be worry because your data is stored and you will start from question number 100 onwards.



 

## 5. Share the score

Why don't share the current and highest score with our peers?

You can share the score right away by just clicking the options menu's Share Score.

I just used Implicit Intent to share the data with the help of android OS.

​
# Final Response

 ![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/58872658/117115141-552d2680-adaa-11eb-9401-8affdb2bc73a.gif)
