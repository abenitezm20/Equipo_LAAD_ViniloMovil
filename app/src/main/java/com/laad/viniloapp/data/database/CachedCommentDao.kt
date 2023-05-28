package com.laad.viniloapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laad.viniloapp.models.Comment

@Dao
interface CachedCommentDao {

    @Query("SELECT * FROM cached_comment")
    fun getComments(): List<Comment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comments: List<Comment>)

    @Query("DELETE FROM cached_comment")
    suspend fun deleteAll(): Int

}
