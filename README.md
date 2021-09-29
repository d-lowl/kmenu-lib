# KMenu
KMenu is a library for writing [rofi scripts](https://www.mankier.com/5/rofi-script) in Kotlin Multiplatform.

## Dependencies
* [rofi](https://github.com/davatorium/rofi)
* [dmenu](https://tools.suckless.org/dmenu/) -- optional, but is supported as the backend (TODO)

## Adding to project
You can get this library from [JitPack](https://jitpack.io/)

```kotlin
repositories {
    //...
    maven(url = "https://jitpack.io")
}

kotlin {
    //...
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("com.github.d-lowl.kmenu-lib:kmenu:1.0")
            }
        }
    }
}
```

## Usage
You can use DSL to define a menu, its options, and corresponding actions

```kotlin
import space.dlowl.kmenu.menu.Menu

fun main(args: Array<String>) {
    Menu.menu {
        option {
            label = "option 1"
            action = { /*do something when selected*/; "" }
        }

        option {
            label = "some other option"
            action = { /*do something else instead*/; "" }
        }
    }.main(args)
}

```

Build the project, copy executable to the path (assuming it's called `kmenu-script`) and run it:
```
rofi -show script -modi "script:kmenu-script"
```

## Examples
For illustration purposes, I've written a small script to manipulate Network Manager connections: [rofi-nm-kmenu](https://github.com/d-lowl/rofi-nm-kmenu)

## Future
There are a few things that I have not coded yet:
- [ ] Sub-menus
- [ ] Support for dmenu and rofi in dmenu mode
- [ ] Support for [row options in rofi](https://www.mankier.com/5/rofi-script#Parsing_row_options)
- [ ] Test with Kotlin JVM (only tested with Kotlin Native so far)
- [ ] Test with kscript