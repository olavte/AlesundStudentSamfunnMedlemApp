To run your app on iOS:

   cd C:\Development\Projects\AstudentApp
   react-native run-ios 
   
   or 
   
   Open ios\AstudentApp.xcodeproj in Xcode Hit the Run button

To run your app on Android:

    First run this: react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest andro id/app/src/main/res
    
    Then this: react-native run-android

   cd C:\Development\Projects\AstudentApp
   Have an Android emulator running (quickest way to get started), or a device connected
   react-native run-android


To use a new icon:

yo rn-toolbox:assets --icon C:\Development\Projects\AstudentApp\icon.png
