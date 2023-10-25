package vn.liam.codebase.base.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    val id: Int?, val preKey: Int?, val nextKey: Int?, val timestamp: Long
)
