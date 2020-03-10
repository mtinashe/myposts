package com.mtinashe.myposts.test_utils

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class CustomRobolectricTestRunner(testClass: Class<*>?) :
    RobolectricTestRunner(testClass) {
    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setPackageName("com.mtinashe.mypost")
            .setManifest("AndroidManifest.xml")
            .build()
    }
}
