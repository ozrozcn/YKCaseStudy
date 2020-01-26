package com.example.yk.base

import androidx.multidex.MultiDexApplication
import com.example.yk.di.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class YKApplicaton : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@YKApplicaton)
            modules(moduleList)
                .printLogger(Level.INFO)
        }
    }
}