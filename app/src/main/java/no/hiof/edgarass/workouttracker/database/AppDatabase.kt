package no.hiof.edgarass.workouttracker.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Exercise::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}