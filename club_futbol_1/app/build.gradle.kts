plugins {
    id("com.google.gms.google-services")//firebase

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {

    buildFeatures{
        viewBinding = true
    }
    namespace = "com.example.club_futbol_1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.club_futbol_1"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    //necesaria para que el usuario pueda registrarse
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    //Cargar imagenes desde URL uso de picaso
    implementation("com.squareup.picasso:picasso:2.71828")
}