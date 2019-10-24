package no.hiof.edgarass.workouttracker.database

import androidx.room.*

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercisedb")
    fun getAll(): List<ExerciseDb>

    @Query("SELECT * FROM exercisedb WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<ExerciseDb>

    @Query("SELECT * FROM exercisedb WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): ExerciseDb

    /* TODO: DB error: DELETE query methods must either return void or int (the number of deleted rows)
    @Query("DELETE FROM exercisedb WHERE name LIKE :name")
    fun deleteByName(name: String) : ExerciseDb
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg exerciseDbs: ExerciseDb)

    @Delete
    fun delete(exerciseDb: ExerciseDb)
}