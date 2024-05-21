import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    kotlin("native.cocoapods")
}

project.tasks.withType(XCFrameworkTask::class).all {
    logger.lifecycle("i find xcframework task ${this.name}")
}

kotlin {
    targetHierarchy.default()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "15"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "SharedKit"
            binaryOptions["bundleId"] = "com.share.module"

            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
