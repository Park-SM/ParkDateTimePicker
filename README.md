![Generic badge](https://img.shields.io/badge/Platform-Android-green.svg)&nbsp;
![Generic badge](https://img.shields.io/badge/Repository-MavenCentral-blue.svg)&nbsp;
![Generic badge](https://img.shields.io/badge/Version-v1.0.0-red.svg)&nbsp;
![Generic badge](https://img.shields.io/badge/License-Apache2.0-3DB7CC.svg)&nbsp;

# ParkDateTimePicker
Android library for selecting date and time from BottomSheet UI. **ParkDateTimePicker** is customizable so you can even specify your own language. If you think this library is useful, please press ⭐️ Star button at upside : )
- [UI Design](https://github.com/Park-SM/ParkDateTimePicker#-ui-design)
    - [DatePicker clip](https://github.com/Park-SM/ParkDateTimePicker#datepicker-clip)
    - [TimePicker clip](https://github.com/Park-SM/ParkDateTimePicker#timepicker-clip)
    - [DateTimePicker clip](https://github.com/Park-SM/ParkDateTimePicker#datetimepicker-clip)
- [How to use](https://github.com/Park-SM/ParkDateTimePicker#-how-to-use)
- [About DatePicker](https://github.com/Park-SM/ParkDateTimePicker#-about-datepicker)
    - [Basic usage](https://github.com/Park-SM/ParkDateTimePicker#basic-usage)
    - [Options](https://github.com/Park-SM/ParkDateTimePicker#options)
- [About TimePicker](https://github.com/Park-SM/ParkDateTimePicker#-about-timepicker)
    - [Basic usage](https://github.com/Park-SM/ParkDateTimePicker#basic-usage-1)
    - [Options](https://github.com/Park-SM/ParkDateTimePicker#options-1)
- [About DateTimePicker](https://github.com/Park-SM/ParkDateTimePicker#-about-datetimepicker)
    - [Basic usage](https://github.com/Park-SM/ParkDateTimePicker#basic-usage-2)
    - [Options](https://github.com/Park-SM/ParkDateTimePicker#options-2)
- [More Sample](https://github.com/Park-SM/ParkDateTimePicker/blob/develop/app/src/main/java/com/smparkworld/sample/MainActivity.kt)
- [License](https://github.com/Park-SM/ParkDateTimePicker#-license)
<br>

## # UI Design
#### *DatePicker clip*
https://user-images.githubusercontent.com/47319426/204150101-829a207e-6cf4-4cb1-a677-601ca03ca9b3.mp4

#### *TimePicker clip*
https://user-images.githubusercontent.com/47319426/204150124-9e7ae320-ebf3-41d2-9b2e-cd9526c8dfbf.mp4

#### *DateTimePicker clip*
https://user-images.githubusercontent.com/47319426/204150160-ec3deaa6-bc2b-4689-800a-707daa7b6f29.mp4

<br>


## # How to use
Add mavenCentral and implementation as below code.
```groovy
// build.gradle(:project)
repositories {
    google()
    mavenCentral()
}

// build.gradle(:app)
dependencies {

    implementation "com.smparkworld.parkdatetimepicker:parkdatetimepicker:1.0.0"
}

// ParkDateTimePicker use DataBinding.
// build.gradle(:app)
android {
    ....
    dataBinding {
        enabled = true
    }
}
```
<br>

## # About DatePicker
To use the DatePicker, you have to set the `DateListener` using the `setDateListener(:DateListener)` function.
#### *Basic usage*
```kotlin
ParkDateTimePicker.builder(this)
    .setDateListener { date ->
        ....
    }
    .show()
```

#### *Options*
```kotlin
ParkDateTimePicker.builder(this)
    .setTitle("Custom Title")
    .setTitle(R.string.custom_title)
    .setDayOfWeekTexts(arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"))
    .setDateTitleFormatter { year, month ->
        "${year}-${String.format("%02d", month)}"
    }
    .setPrimaryColor("#FF0000")
    .setPrimaryColor(R.color.red)
    .setHighLightColor("#00FF00")
    .setHighLightColor(R.color.green)
    .setDateListener { date ->
        ....
    }
    .show()
```
<br>

## # About TimePicker
To use the TimePicker, you have to set the `TimeListener` using the `setTimeListener(:TimeListener)` function.
#### *Basic usage*
```kotlin
ParkDateTimePicker.builder(this)
    .setTimeListener { time ->
        ....
    }
    .show()

```

#### *Options*
```kotlin
ParkDateTimePicker.builder(this)
    .setTitle("Custom Title")
    .setTitle(R.string.custom_title)
    .setAmPmTexts(arrayOf("CustomAM", "CustomPM"))
    .setTimeDoneText("Done!!")
    .setTimeTitleFormatter { amPm, hour, minute ->
        "$amPm ${hour}h ${minute}m"
    }
    .setPrimaryColor("#FF0000")
    .setPrimaryColor(R.color.red)
    .setHighLightColor("#00FF00")
    .setHighLightColor(R.color.green)
    .setTimeListener { time ->
        ....
    }
    .show()
```
<br>

## # About DateTimePicker
To use the DateTimePicker, you have to set the `DateTimeListener` using the `setDateTimeListener(:DateTimeListener)` function.
#### *Basic usage*
```kotlin
ParkDateTimePicker.builder(this)
    .setDateTimeListener { dateTime ->
        ....
    }
    .show()
```

#### *Options*
```kotlin
ParkDateTimePicker.builder(this)
    .setTitle("Custom Title")
    .setTitle(R.string.custom_title)
    .setAmPmTexts(arrayOf("CustomAM", "CustomPM"))
    .setDayOfWeekTexts(arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"))
    .setTimeDoneText("Done!!")
    .setDateTitleFormatter { year, month ->
        "${year}-${String.format("%02d", month)}"
    }
    .setTimeTitleFormatter { amPm, hour, minute ->
        "$amPm ${hour}h ${minute}m"
    }
    .setPrimaryColor("#FF0000")
    .setPrimaryColor(R.color.red)
    .setHighLightColor("#00FF00")
    .setHighLightColor(R.color.green)
    .setDateTimeListener { dateTime ->
        ....
    }
    .show()
```
<br>

## # License
```
Copyright 2022 ParkSM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
