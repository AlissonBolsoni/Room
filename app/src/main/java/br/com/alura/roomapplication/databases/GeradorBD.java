package br.com.alura.roomapplication.databases;

import android.arch.persistence.room.Room;
import android.content.Context;

public class GeradorBD {

    public DatabaseRoom geraBD(Context context){
        return Room.databaseBuilder(context, DatabaseRoom.class, "RoomDB").allowMainThreadQueries().build();
    }
}
