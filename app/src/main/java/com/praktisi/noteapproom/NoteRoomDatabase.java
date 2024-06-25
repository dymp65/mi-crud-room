package com.praktisi.noteapproom;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase{
    private static NoteRoomDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteRoomDatabase.class, "note_app")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
