package no.hiof.edgarass.workouttracker.model

data class Exercise (var name : String, var sets : Int, var reps : Int, var weight : Int, var unit : String = "", var increment : Float = 2.5f) {

    companion object {
        var data = ArrayList<Exercise>()
        fun getExercises(): ArrayList<Exercise> {
            return data
        }

        fun addExercises(exercise: Exercise) {
            if (!data.contains(exercise)) {
                data.add(exercise)
            }
        }
    }
}