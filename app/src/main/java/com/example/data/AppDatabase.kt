package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "intruder_logs")
data class IntruderLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val type: String // "Failed PIN", "Fake Password", etc.
)

@Dao
interface IntruderLogDao {
    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<IntruderLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: IntruderLog)
    
    @Query("DELETE FROM intruder_logs")
    suspend fun clearLogs()
}

@Database(entities = [IntruderLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun intruderLogDao(): IntruderLogDao
}
