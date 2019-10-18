package no.hiof.edgarass.workouttracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
data class ExerciseDb (
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "sets") val sets: Int?,
    @ColumnInfo(name = "reps") val reps: Int?,
    @ColumnInfo(name = "weight") val weight: Int?,
    @ColumnInfo(name = "unit") val unit: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}