package no.hiof.edgarass.workouttracker.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromDBtoModel (exerciseDb : ExerciseDb) : no.hiof.edgarass.workouttracker.model.Exercise{
        return no.hiof.edgarass.workouttracker.model.Exercise(
            exerciseDb.name.toString(),
            exerciseDb.sets.toString().toInt(),
            exerciseDb.reps.toString().toInt(),
            exerciseDb.weight.toString().toInt(),
            exerciseDb.unit.toString())
    }
}