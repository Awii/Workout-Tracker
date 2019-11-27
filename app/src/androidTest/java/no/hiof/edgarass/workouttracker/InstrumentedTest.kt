package no.hiof.edgarass.workouttracker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("no.hiof.edgarass.workouttracker", appContext.packageName)
    }

    @Test
    fun addEditFinishDeleteExercise() {
        // Run sequentially
        onView(withId(R.id.floating_action_button_add_exercise)).perform(click())
        onView(withId(R.id.addExerciseName)).perform(typeText("Name")).perform(closeSoftKeyboard())
        onView(withId(R.id.addExerciseSets)).perform(typeText("1")).perform(closeSoftKeyboard())
        onView(withId(R.id.addExerciseReps)).perform(typeText("3")).perform(closeSoftKeyboard())
        onView(withId(R.id.addExerciseWeight)).perform(typeText("40")).perform(closeSoftKeyboard())
        onView(withId(R.id.addExerciseUnit)).perform(typeText("kg")).perform(closeSoftKeyboard())
        onView(withId(R.id.addExerciseIncrement)).perform(typeText("2.5")).perform(closeSoftKeyboard())
        onView(withId(R.id.btn_add)).perform(click())

        onView(withId(R.id.exercise_recycle_view)).perform(longClick())
        onView(withId(R.id.editExerciseName)).perform(replaceText("NewName")).perform(closeSoftKeyboard())
        onView(withId(R.id.editExerciseSets)).perform(replaceText("2")).perform(closeSoftKeyboard())
        onView(withId(R.id.editExerciseReps)).perform(replaceText("4")).perform(closeSoftKeyboard())
        onView(withId(R.id.editExerciseWeight)).perform(replaceText("50")).perform(closeSoftKeyboard())
        onView(withId(R.id.editExerciseUnit)).perform(replaceText("kg")).perform(closeSoftKeyboard())
        onView(withId(R.id.editExerciseIncrement)).perform(replaceText("0")).perform(closeSoftKeyboard())
        onView(withId(R.id.btn_edit)).perform(click())

        onView(withId(R.id.floating_action_button_finish_workout)).perform(click())
        onView(withId(R.id.confirm_button)).perform(click())

        onView(withId(R.id.exercise_recycle_view)).perform(longClick())
        onView(withId(R.id.btn_delete)).perform(click())
    }
}
