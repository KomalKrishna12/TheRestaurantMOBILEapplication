package com.example.therestaurantmobileapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {
    @Insert
    void registerUser(User user);
    @Query("SELECT * FROM usertable WHERE userID=(:userID) and password=(:pass)")
    User login(String userID,String pass);
    @Query("SELECT Name FROM usertable WHERE userID=(:userID_for_name) and password=(:psw_for_name)")
    String getName(String userID_for_name,String psw_for_name);
    @Query("SELECT userID FROM usertable WHERE password=(:psw_for_uname)")
    String getUserID(String psw_for_uname);
}
