/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package br.com.alisson.roomapp.dependencyinjection

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import br.com.alisson.roomapp.data.ListItemDao
import br.com.alisson.roomapp.data.ListItemDatabase
import br.com.alisson.roomapp.data.ListItemRepository
import br.com.alisson.roomapp.viewmodel.CustomViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by R_KAY on 8/18/2017.
 */
@Module
class RoomModule(application: Application) {

    private val database: ListItemDatabase

    init {
        this.database = Room.databaseBuilder(
            application,
            ListItemDatabase::class.java!!,
            "ListItem.db"
        ).build()
    }

    @Provides
    @Singleton
    internal fun provideListItemRepository(listItemDao: ListItemDao): ListItemRepository {
        return ListItemRepository(listItemDao)
    }

    @Provides
    @Singleton
    internal fun provideListItemDao(database: ListItemDatabase): ListItemDao {
        return database.listItemDao()
    }

    @Provides
    @Singleton
    internal fun provideListItemDatabase(application: Application): ListItemDatabase {
        return database
    }

    @Provides
    @Singleton
    internal fun provideViewModelFactory(repository: ListItemRepository): ViewModelProvider.Factory {
        return CustomViewModelFactory(repository)
    }
}
