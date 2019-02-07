package br.com.alisson.roomapp

import android.app.Application
import br.com.alisson.roomapp.dependencyinjection.ApplicationComponent
import br.com.alisson.roomapp.dependencyinjection.RoomModule
import br.com.alisson.roomapp.dependencyinjection.ApplicationModule
import br.com.alisson.roomapp.dependencyinjection.DaggerApplicationComponent


class RoomAppApplication: Application() {
    private var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()

    }

    fun getApplicationComponent(): ApplicationComponent? {
        return applicationComponent
    }

}