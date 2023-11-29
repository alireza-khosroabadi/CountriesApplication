@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id("kotlin")
    id("idea")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.google.autoService)
    implementation(project(":compose-navigation-annotation"))
    kapt(libs.google.autoService)
    implementation(libs.squareup.javapoet)
    implementation(libs.squareup.kotlinpoet)
    implementation (libs.guava)

//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.android)
//    implementation(libs.androidx.core.ktx)

//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.compose.ui)
}

sourceSets {
    main {
        java {
            srcDir("${buildDir.absolutePath}/tmp/kapt/main/kotlinGenerated/")
        }
    }
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}