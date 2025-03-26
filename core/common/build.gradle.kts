plugins {
    id("com.joker.coolmall.android.library")
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))
}