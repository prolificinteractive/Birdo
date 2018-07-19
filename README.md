<img src="/assets/hero.jpg"/>

# Birdo
[![Travis branch](https://img.shields.io/travis/prolificinteractive/birdo/master.svg)](https://travis-ci.org/prolificinteractive/birdo) [![](https://jitpack.io/v/prolificinteractive/birdo.svg)](https://jitpack.io/#prolificinteractive/birdo)

Prolific's wrapper for debugging their application. 

Birdo is based on DebugDrawer (https://github.com/palaima/DebugDrawer). 

## Installation

Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```groovy
dependencies {
  implementation 'com.github.prolificinteractive:birdo:${birdoVersion}'
}
```

## Usage

```kotlin
class App : Application() {
  override fun onCreate() {
    super.onCreate()

    // Add this to your application class. Detect shakes and launch the default Birdo view.
    ShakerDetector(this, BirdoInitializer(this))
  }
}
```

## Customization

### Detectors

The detectors are utility classes that will launch Birdo upon a certain set of predefined user actions.

#### ShakerDetector

The shaker detector is simply relying on shakes movement to launch Birdo. To use it, simply initialize the detector in your application class, like so:

```kotlin
class App : Application() {
  override fun onCreate() {
    super.onCreate()

    // Add this to your application class. Detect shakes and launch the default Birdo view.
    ShakerDetector(this, BirdoInitializer(this))
  }
}
```

#### VolumeDownDetector

The volume down detector is looking for the user pressing 3 times volume down. To use it, you should add the detector to your base activity:

```kotlin
open abstract class BaseActivity : AppCompatActivity() {
  private lateinit var keyUpDetector: KeyUpDetector

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    keyUpDetector = VolumeDownDetector(BirdoInitializer(this))
  }

  override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
    keyUpDetector.onKeyUp(this, keyCode, event)
    return super.onKeyUp(keyCode, event)
  }
}
```

Feel free to implement your own version of KeyUpDetector with your own combination of keys.

#### Your Own

You can also simply launch Birdo your way:

```kotlin
val birdoInitializer = BirdoInitializer(this)
button.setOnClickListener { birdoInitializer.start(this) }
```

### Custom Birdo Modules

Birdo comes with a set of predefined modules. You can always customize Birdo by providing your own implementation to the BirdoInitializer.

For example:
```kotlin
ShakerDetector(this, BirdoInitializer(this, MyBirdoActivity::class.java))
```

Extending of BirdoActivity allows you to provide your Picasso or OkHttpClient instance, and provide your own custom modules.


## Contributing to Birdo

To report a bug or enhancement request, feel free to file an issue under the respective heading.

If you wish to contribute to the project, fork this repo and submit a pull request. Code contributions should follow the standards specified in the [Prolific Android Style Guide](https://github.com/prolificinteractive/android-code-styles).

## License

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Copyright (c) 2018 Prolific Interactive

Birdo is maintained and sponsored by Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: ./LICENSE
