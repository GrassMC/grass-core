dependencies {
    api(kotlin("reflect"))
    api(platform(libs.kotlinx.coroutines.bom))
    api(libs.kotlinx.coroutines.core)
    api(libs.bundles.mccoroutine.folia)
}
