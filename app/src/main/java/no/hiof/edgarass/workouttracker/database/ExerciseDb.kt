package no.hiof.edgarass.workouttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseDb (
    @ColumnInfo(name = "routine") val routine: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "sets") val sets: Int?,
    @ColumnInfo(name = "reps") val reps: Int?,
    @ColumnInfo(name = "weight") val weight: Double?,
    @ColumnInfo(name = "unit") val unit: String?,
    @ColumnInfo(name = "increment") val increment: Double?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}