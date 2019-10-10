package no.hiof.edgarass.workouttracker.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    fun getAll(): List<Exercise>

    @Query("SELECT * FROM exercise WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Exercise>

    @Query("SELECT * FROM exercise WHERE exercise_name LIKE :name LIMIT 1")
    fun findByName(name: String): Exercise

    @Insert
    fun insertOne(exercise: Exercise) {
    }

    @Insert
    fun insertAll(vararg exercises: Exercise)

    @Delete
    fun delete(exercise: Exercise)
}