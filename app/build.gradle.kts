import java.net.URL

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.openapi.generator") version "7.6.0" // adjust to latest
}

android {
    namespace = "com.example.sarpapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sarpapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 3
        versionName = "1.0."+versionCode

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.animation:animation:1.7.7")
    // Choose one of the following:
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // or Material Design 2
    implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation("androidx.compose.material3.adaptive:adaptive")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.9.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    implementation("androidx.compose.runtime:runtime-rxjava2")
    // for http requests
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    //for http interface
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //for json converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //loggin interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}


tasks.register<DefaultTask>("downloadOpenApiSpec") {
    doLast {
        val openApiURL = "https://sarp01.westeurope.cloudapp.azure.com/swagger/json"

        val url = URL(openApiURL)
        val file = File("$rootDir/specs/openapi.yaml")
        file.parentFile.mkdirs()
        file.writeText(url.readText())
    }
}

tasks.named("openApiGenerate") {
    dependsOn("downloadOpenApiSpec")
}

tasks.named("assemble") {
    dependsOn("openApiGenerate")
}


openApiGenerate {
    generatorName.set("kotlin")
    skipValidateSpec.set(true)

    inputSpec.set("$rootDir/specs/openapi.json") // You'll copy the file locally
    outputDir.set("$buildDir/generated/openapi")


    apiPackage.set("com.example.sarpapp.data.api")
    modelPackage.set("com.example.sarpapp.data.model")
    invokerPackage.set("com.example.sarpapp.data.invoker")

    configOptions.set(
        mapOf(
            "library" to "jvm-ktor",              // ← use Ktor-based client
            "dateLibrary" to "java8",         // or "string", "java8", etc.
            "serializationLibrary" to "kotlinx_serialization",
            "useCoroutines" to "true"
        )
    )
}