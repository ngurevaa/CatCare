plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.kpfu.itis.gureva.catcare"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.kpfu.itis.gureva.catcare"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CAT_FACT_API_BASE_URL", "\"https://meowfacts.herokuapp.com\"")
        buildConfigField("String", "CAT_GIF_BASE_URL", "\"https://cataas.com/cat/gif\"")
        buildConfigField("String", "CAT_ERROR_BASE_URL", "\"https://http.cat/\"")
        buildConfigField("String", "CAT_FACT_LANG_VALUE", "\"rus\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    val roomVersion = "2.4.3"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    val dotsIndicatorVersion = "5.0"
    implementation("com.tbuonomo:dotsindicator:$dotsIndicatorVersion")

    val daggerVersion = "2.50"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    val fragmentVersion = "1.6.2"
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")

    val coroutinesVersion = "1.3.9"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    val lifecycleVersion = "2.4.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
