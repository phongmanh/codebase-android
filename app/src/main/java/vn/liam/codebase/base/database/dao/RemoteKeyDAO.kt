package vn.liam.codebase.base.database.dao

import androidx.room.Dao
import androidx.room.Query
import vn.liam.codebase.base.models.RemoteKey

@Dao
interface RemoteKeyDao : IDao<RemoteKey> {

    @Query("SELECT * FROM remote_keys WHERE id=:id")
    suspend fun getRemoteKey(id: Int): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

    @Query("SELECT MAX(timestamp) FROM remote_keys")
    suspend fun getLatestUpdated(): Long?
}