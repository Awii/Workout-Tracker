package no.hiof.edgarass.workouttracker.database

import androidx.room.*

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercisedb")
    fun getAll(): List<ExerciseDb>

    @Query("SELECT * FROM exercisedb WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): ExerciseDb

    @Query("UPDATE exercisedb SET routine = :routine, name = :name, sets = :sets, reps = :reps, weight = :weight, unit = :unit, increment = :increment WHERE name LIKE :oldName")
    fun updateExercise(oldName : String, routine : String, name: String, sets : Int, reps : Int, weight : Double, unit : String, increment : Double)

    @Query("UPDATE exercisedb SET weight = :weight, increment = :increment WHERE name LIKE :name")
    fun updateWeight(name: String, weight : Double, increment : Double)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg exerciseDbs: ExerciseDb)

    @Delete
    fun delete(exerciseDb: ExerciseDb)

    @Query("DELETE FROM exercisedb")
    fun deleteAll()

}