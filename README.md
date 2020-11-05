# Trivia
### Convert your JSON Data into a nice Test App with a Responsive Animation 

#### JSON Data Link for questions [https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json](url)

#### Let's Show this data as a nice  Test App 

#### Let's see what I have done.....

![Trivia](https://user-images.githubusercontent.com/58872658/98260363-41466a80-1fa9-11eb-9f6e-f50b0ae2df2e.gif)

#### If your answer is _False_ then you will get a nice animation with _Red color_ 

- ##### XML Code and properties for red shake animation

`<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="150"
    android:fromDegrees="-15"
    android:pivotX="30%"
    android:pivotY="30%"
    android:repeatCount="1"
    android:repeatMode="reverse"
    android:toDegrees="10" />`
    

 #### When your answer is _Right_ then There is _Green Colored Animation_ which will be active for _200 ms_ and will _repeat 3 times_ throughout this duration

- ##### Animation properties for green one 

`AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setRepeatCount(3);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);`
