object Dependencies {

    val androidxNavFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}" }
    val androidxNavSafeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}" }
    val androidxNavUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}" }

    val retrofit2 by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val gson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.gson}" }
    val httpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptor}" }

    val rxJava3Android by lazy { "io.reactivex.rxjava3:rxandroid:${Versions.rxJavaAndroid}" }
    val rxJava3Kotlin by lazy { "io.reactivex.rxjava3:rxkotlin:${Versions.rxJavaKotlin}" }
    val rxJava by lazy { "io.reactivex.rxjava3:rxjava:${Versions.rxJava}" }
    val rxJavaCallAdapter by lazy { "com.squareup.retrofit2:adapter-rxjava3:${Versions.rxJavaCallAdapter}" }

    val androidxCoreKtx by lazy { "androidx.core:core-ktx:${Versions.androidxCoreKtx}" }
    val androidxLifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}" }

    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomRxJava3 by lazy { "androidx.room:room-rxjava3:${Versions.room}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }

    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraint by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraint}" }
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val testExtJunit by lazy { "androidx.test.ext:junit:${Versions.testExtJunit}" }
    val mockitoCore by lazy { "org.mockito:mockito-core:${Versions.mockitoCore}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }

}
