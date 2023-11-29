@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.alireza.countriesapplication"
    compileSdk = libs.versions.app.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.alireza.countriesapplication"
        minSdk = libs.versions.app.minSdk.get().toInt()
        targetSdk = libs.versions.app.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.alireza.countriesapplication.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            //isMinifyEnabled = true
            //isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    // To access resources file in test package
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    // To access assets in androidTest package
    sourceSets {
        getByName("androidTest").assets.srcDirs("src/debug/assets")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += "environment"
    productFlavors {
        create("demo") {
            dimension = "environment"
            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
        }
        create("production") {
            dimension = "environment"
            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
        }
    }
}

dependencies {

    // Projects
    implementation(project(":uiSystem"))
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    //Hilt
    implementation(libs.hilt.android)
    implementation(project(":compose-navigation-annotation"))
    implementation(project(":compose-navigation-kapt"))
    kapt(libs.hilt.compiler)

    androidTestImplementation(platform(libs.androidx.compose.bom))


    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)

    debugImplementation(libs.androidx.compose.ui.tooling)

    // Test
    testImplementation(libs.bundles.test)
    testImplementation(libs.bundles.apollo.test)


    // Android Test
    androidTestImplementation(libs.bundles.test.ui)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    kaptAndroidTest(libs.hilt.kapt.test)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apollo {
    service("service") {
        packageName.set("com.alireza")

        // Enable test builder generation
        generateTestBuilders.set(true)
    }
}