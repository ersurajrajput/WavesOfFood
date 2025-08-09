import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}
val localProperties = Properties().apply {
    load(File(rootProject.rootDir, "local.properties").inputStream())
}
val cloudinaryUrl: String = project.findProperty("cloudinary_url") as String? ?: ""
val google_map_api_key: String = project.findProperty("google_map_api_key") as String? ?: ""



android {
    namespace = "com.example.wavesoffood"
    compileSdk = 36
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.wavesoffood"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField(
            "String",
            "FIREBASE_DB_URL",
            "\"${localProperties["firebaseDbUrl"]}\""
        )
        buildConfigField(
            "String",
            "cloudinary_url",
            "\"${localProperties["cloudinary_url"]}\""
        )
        defaultConfig {

            // Inject from local.properties
            manifestPlaceholders["cloudinary_url"] = cloudinaryUrl
        }
        defaultConfig {

            // Inject from local.properties
            manifestPlaceholders["google_map_api_key"] = google_map_api_key
        }
        buildConfigField(
            "String",
            "cloudinary_cloud_name",
            "\"${localProperties["cloudinary_cloud_name"]}\""
        )
        buildConfigField(
            "String",
            "cloudinary_api_key",
            "\"${localProperties["cloudinary_api_key"]}\""
        )
        buildConfigField(
            "String",
            "cloudinary_api_secret",
            "\"${localProperties["cloudinary_api_secret"]}\""
        )

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
    implementation(libs.firebase.database)

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //cloudinary
    implementation("com.cloudinary:cloudinary-android:3.0.2")

    //location
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}