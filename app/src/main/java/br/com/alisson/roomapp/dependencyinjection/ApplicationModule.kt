package br.com.alisson.roomapp.dependencyinjection

import android.app.Application
import br.com.alisson.roomapp.RoomAppApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: RoomAppApplication) {

    @Provides
    internal fun provideRoomDemoApplication(): RoomAppApplication {
        return application
    }

    @Provides
    internal fun provideApplication(): Application {
        return application
    }
}
