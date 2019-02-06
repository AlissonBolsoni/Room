package br.com.alisson.roomapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(version = 1,
    entities = [
        ListItem::class
    ])
abstract class ListItemDatabase : RoomDatabase(){

    abstract fun listItemDao(): ListItemDao

}