package task.habits

import android.content.Intent
import kotlinx.android.synthetic.main.habit_editor_container.*
import java.io.Serializable

data class HabitInfo(
    val HabitName: String,
    val HabitDescription: String,
    val HabitDoneCount: Int,
    val HabitPeriodicity: Int,
    val HabitPriorityValue: HabitPriority,
    //var HabitTypeValue: HabitType,
    var HabitColor: String): Serializable

class HabitInfoHelper{

    fun fillIntentWithFormEditorInfo(habitEditorFragment: HabitEditorFragment, intent: Intent){
//        val habitType = when (habitEditorFragment.editor_radioGroup_habit_type.checkedRadioButtonId) {
//            habitEditorFragment.editor_radio_good_habit.id -> "Good"
//            else -> "Bad"
//        }
        val habitPriority = when (habitEditorFragment.editor_habit_priority.selectedItem.toString()){
            "низкий" -> "Low"
            "средний" -> "Medium"
            else -> "High"
        }

        var periodicity = habitEditorFragment.editor_habit_periodicity.text.toString()
        if (periodicity == "")
            periodicity = "0"

        var habitDoneCount = habitEditorFragment.editor_habit_done_count.text.toString()
        if (habitDoneCount == "")
            habitDoneCount = "0"

        intent.putExtra("habitName", habitEditorFragment.editor_habit_name.text.toString())
        intent.putExtra("habitDescription", habitEditorFragment.editor_habit_description.text.toString())
        intent.putExtra("habitPeriodicity", periodicity.toInt())
        intent.putExtra("habitPriority", habitPriority)
        //intent.putExtra("habitType", habitType)
        intent.putExtra("habitColor", habitEditorFragment.editor_habit_color.text.toString())
        intent.putExtra("habitDoneCount", habitDoneCount.toInt())
    }

    fun getHabitInfoFromIntent(intent: Intent?): HabitInfo?{
        if (intent == null)
            return null

        return HabitInfo(
            HabitName = intent.getStringExtra("habitName") ?: "",
            HabitDescription = intent.getStringExtra("habitDescription") ?: "",
            HabitDoneCount = intent.getIntExtra("habitDoneCount", 0),
            HabitColor = intent.getStringExtra("habitColor") ?: "White",
            //HabitTypeValue = HabitType.valueOf(intent.getStringExtra("habitType") ?: "Good"),
            HabitPriorityValue = HabitPriority.valueOf(intent.getStringExtra("habitPriority") ?: "Low"),
            HabitPeriodicity = intent.getIntExtra("habitPeriodicity", 0)
        )
    }

    fun fillEditorForm(habitEditorFragment: HabitEditorFragment, habitInfo: HabitInfo?){
        if (habitInfo == null)
            return

        habitEditorFragment.editor_habit_name.setText(habitInfo.HabitName)
        habitEditorFragment.editor_habit_description.setText(habitInfo.HabitDescription)
        habitEditorFragment.editor_habit_color.setText(habitInfo.HabitColor)
        habitEditorFragment.editor_habit_done_count.setText(habitInfo.HabitDoneCount.toString())
        habitEditorFragment.editor_habit_periodicity.setText(habitInfo.HabitPeriodicity.toString())
//        if (habitInfo.HabitTypeValue == HabitType.Bad){
//            habitEditorFragment.editor_radio_bad_habit.isChecked = true
//            habitEditorFragment.editor_radio_good_habit.isChecked = false
//        }
//        else{
//            habitEditorFragment.editor_radio_bad_habit.isChecked = false
//            habitEditorFragment.editor_radio_good_habit.isChecked = true
//        }

        when (habitInfo.HabitPriorityValue){
            HabitPriority.Medium -> habitEditorFragment.editor_habit_priority.setSelection(1)
            HabitPriority.High -> habitEditorFragment.editor_habit_priority.setSelection(2)
            else -> habitEditorFragment.editor_habit_priority.setSelection(0)
        }
    }
}