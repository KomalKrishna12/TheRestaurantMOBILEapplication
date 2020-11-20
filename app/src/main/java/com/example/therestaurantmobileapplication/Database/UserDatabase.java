package com.example.therestaurantmobileapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = User.class,version=1)
public abstract class UserDatabase extends RoomDatabase {
    public static UserDatabase instance;
    public abstract UserDAO userDAO();
    public static synchronized UserDatabase getInstance(Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,"usertable")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
