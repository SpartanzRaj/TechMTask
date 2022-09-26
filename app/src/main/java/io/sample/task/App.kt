package io.sample.task

import android.app.*
import org.koin.android.ext.koin.*
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(this@App)
            // use modules
            modules(appModule)
        }
    }
}