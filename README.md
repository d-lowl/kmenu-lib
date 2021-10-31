# KMenu
KMenu is a library for writing [rofi scripts](https://www.mankier.com/5/rofi-script) in Kotlin Multiplatform.

## Dependencies
* [rofi](https://github.com/davatorium/rofi)

## Adding to project
You can get this library from GitLab Package Registry

```kotlin
repositories {
    //...
    maven(url = "https://gitlab.com/api/v4/projects/30308398/packages/maven")
}

kotlin {
    //...
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("space.dlowl:kmenu:1.1-SNAPSHOT")
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

## Reference
The library provides DSL for building menus.
### Menu
Create and call the menu from the main function. 

```kotlin
import space.dlowl.kmenu.menu.Menu

fun main(args: Array<String>) {
    Menu.menu {
        title = "kmenu-example" // Rofi menu title, optional
        
        //You can add menu options and submenus
        //option and submenu blocks can be repeated
        option {...}
        
        submenu {...}
    }.main(args)
}
```
### Option
```kotlin
<...>
        // option block defines menu option appearance and 
        // function to be executed if an option is selected
        option {
            label = "Option 1" // A string to be displayed in rofi
            key = "option1" // An arbitrary key, must be unique within one menu
            func = { ... } // A function to execute, of type (RofiInput) -> Unit
        }

        // You can also define non-selectable options
        option {
            label = "Option 2"
            nonselectable = true
        }
<...>
```

### Submenu
```kotlin
<...>
        // submenu blocks follow similar structure as the top level menu
        submenu {
            title = "Submenu 1" // a string to be displayed in a parent menu
            key = "submenu1" // An arbitrary key, must be unique within one menu
            
            // Followed by option and submenu blocks as necessary
        }
<...>
```

## Examples
For illustration purposes, I've written a small script to manipulate Network Manager connections: [rofi-nm-kmenu](https://gitlab.com/dlowl/rofi-nm-kmenu)

## Future
There are a few things that I have not coded yet:
- [x] Sub-menus
- [ ] Custom selections
- [ ] Support for [row options in rofi](https://www.mankier.com/5/rofi-script#Parsing_row_options)
- [ ] Test with Kotlin JVM (only tested with Kotlin Native so far)
- [ ] Test with kscript