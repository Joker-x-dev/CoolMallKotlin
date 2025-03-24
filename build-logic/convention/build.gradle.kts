plugins {
    `kotlin-dsl`
}

group = "com.joker.coolmall.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.joker.coolmall.android.application"
            implementationClass = "com.joker.coolmall.AndroidApplication"
        }
        register("androidLibrary") {
            id = "com.joker.coolmall.android.library"
            implementationClass = "com.joker.coolmall.AndroidLibrary"
        }
        register("androidCompose") {
            id = "com.joker.coolmall.android.compose"
            implementationClass = "com.joker.coolmall.AndroidCompose"
        }
        register("androidFeature") {
            id = "com.joker.coolmall.android.feature"
            implementationClass = "com.joker.coolmall.AndroidFeature"
        }
        register("androidTest") {
            id = "com.joker.coolmall.android.test"
            implementationClass = "com.joker.coolmall.AndroidTest"
        }
    }
} 