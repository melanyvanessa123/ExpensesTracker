plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.expensestracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expensestracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
}

dependencies {

    implementation(platform("androidx.compose:compose-bom:2023.10.01"))


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.compiler:compiler:1.5.1")


    implementation("com.google.android.gms:play-services-auth:20.7.0")


    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.3.2")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.3.2")


        implementation("io.github.jan-tennert.supabase:postgrest-kt:1.4.7")
        implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.7")
    implementation ("com.google.android.material:material:1.9.0")

        implementation("io.ktor:ktor-client-android:2.3.7")
        implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.7")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.4.7")
    implementation("io.ktor:ktor-client-android:2.3.7")
    implementation("io.ktor:ktor-client-core:2.3.7")

    implementation("io.ktor:ktor-client-android:2.3.5")
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-cio:2.3.5")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}