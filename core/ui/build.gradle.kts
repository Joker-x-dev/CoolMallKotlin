plugins {
    id("com.joker.coolmall.android.library")
    id("com.joker.coolmall.android.compose")
}

android {
    namespace = "com.joker.coolmall.core.ui"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:designsystem"))
}