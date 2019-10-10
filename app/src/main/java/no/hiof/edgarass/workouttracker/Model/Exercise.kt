package no.hiof.edgarass.workouttracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "exercise_name") val exerciseName: String?,
    @ColumnInfo(name = "sets") val sets: Int?,
    @ColumnInfo(name = "reps") val reps: Int?,
    @ColumnInfo(name = "weight") val weight: Int?,
    @ColumnInfo(name = "unit") val unit: String?
)