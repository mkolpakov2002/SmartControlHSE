package ru.hse.smart_control.model.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.hse.smart_control.model.entities.UniversalSchemeEntity

@Dao
interface DeviceItemTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: UniversalSchemeEntity)

    @Query("DELETE FROM deviceOld WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM deviceOld")
    suspend fun deleteAll()

    @Update
    suspend fun update(item: UniversalSchemeEntity)

    @Query("SELECT * FROM deviceOld")
    fun getAll(): Flow<List<UniversalSchemeEntity>>
}