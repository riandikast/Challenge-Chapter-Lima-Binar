package com.binar.challengechapterlima.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun registerUser(user: User):Long

    @Query("SELECT * FROM User WHERE User.email = :email")
    fun getUserRegis(email:String): User
}