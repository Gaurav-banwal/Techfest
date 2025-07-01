plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.techfest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.techfest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding{
        enable = true

    }
}

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
//    implementation ("androidx.drawerlayout:drawerlayout:1.1.1")
//
//    // Firebase BoM
//    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
//    implementation("com.google.firebase:firebase-auth")
//    implementation("com.google.firebase:firebase-firestore-ktx")
//
//
//
//    implementation(libs.androidx.core.ktx)
//
//
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.firebase.auth)
//
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.gridlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
    implementation("androidx.drawerlayout:drawerlayout:1.1.1")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // AndroidX + Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}