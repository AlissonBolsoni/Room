package br.com.alisson.roomapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class ListItem(
    @PrimaryKey var itemId: String = "",
    var message: String?,
    var colorResource: Int)
