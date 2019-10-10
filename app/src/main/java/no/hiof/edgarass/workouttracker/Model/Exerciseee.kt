package no.hiof.edgarass.workouttracker.Model

import android.widget.TextView

// TODO: fix Exerciseee class (extend TextView?)

data class Exerciseee (var name : String, var sets : Int, var reps : Int, var weight : Int, var unit : String = "") {

    override fun toString(): String {
        return name + " " + sets.toString() + "x" + reps.toString() + " " + weight.toString() + unit
    }
}