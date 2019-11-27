package no.hiof.edgarass.workouttracker.model

data class Exercise (var routine : String, var name : String, var sets : Int, var reps : Int, var weight : Double, var unit : String = "", var increment : Double) {

    companion object {
        private var data = ArrayList<Exercise>()
        fun getExercises(): ArrayList<Exercise> {
            return data
        }
    }
}