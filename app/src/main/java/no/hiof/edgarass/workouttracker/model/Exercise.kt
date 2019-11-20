package no.hiof.edgarass.workouttracker.model

data class Exercise (var routine : String, var name : String, var sets : Int, var reps : Int, var weight : Double, var unit : String = "", var increment : Double) {

    companion object {
        var data = ArrayList<Exercise>()
        fun getExercises(): ArrayList<Exercise> {
            return data
        }

        fun addExercise(exercise: Exercise) {
            if (!data.contains(exercise)) {
                data.add(exercise)
            }
        }

        fun removeExercise(name: String) {
            for (ex in data) {
                if (ex.name == name) {
                    data.remove(ex)
                    return
                }
            }
        }
    }
}