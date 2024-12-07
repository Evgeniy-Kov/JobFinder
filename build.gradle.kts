// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("convention.detekt")
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.0.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.4" apply false
}
