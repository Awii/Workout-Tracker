package no.hiof.edgarass.workouttracker.model

// TODO: fix Exercise class (extend TextView?)

data class Exercise (var name : String, var sets : Int, var reps : Int, var weight : Int, var unit : String = "", var increment : Float = 2.5f) {

    companion object {
        fun getExercises() : ArrayList<Exercise> {
            val data = ArrayList<Exercise>()

            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Deadlift", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Bench press", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Squat", 4, 6, 50, "kg", 5f))
            data.add(Exercise("Running", 4, 6, 50, "kg", 5f))
            return data

        }
    }


    override fun toString(): String {
        return name + " " + sets.toString() + "x" + reps.toString() + " " + weight.toString() + unit
    }
}