package task.habits

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.habit_editor_container.*
import java.io.Serializable

data class HabitInfo(
    val HabitName: String,
    val HabitDescription: String,
    val HabitDoneCount: Int,
    val HabitPeriodicity: Int,
    val HabitPriorityValue: HabitPriority) : Serializable

class HabitInfoHelper{

    fun getHabitInfoFromEditorInfo(habitEditorFragment: HabitEditorFragment): HabitInfo{
        val view = habitEditorFragment.view
        val habitPriority = when (view?.findViewById<Spinner>(R.id.editor_habit_priority)?.selectedItem.toString()){
            "низкий" -> HabitPriority.Low
            "средний" -> HabitPriority.Medium
            else -> HabitPriority.High
        }

        var periodicity = view?.findViewById<EditText>(R.id.editor_habit_periodicity)?.text.toString()
        if (periodicity == "")
            periodicity = "0"

        var habitDoneCount =  view?.findViewById<EditText>(R.id.editor_habit_done_count)?.text.toString()
        if (habitDoneCount == "")
            habitDoneCount = "0"

        return HabitInfo(
            HabitName =  view?.findViewById<EditText>(R.id.editor_habit_name)?.text.toString(),
            HabitDescription =  view?.findViewById<EditText>(R.id.editor_habit_description)?.text.toString(),
            HabitPeriodicity = periodicity.toInt(),
            HabitPriorityValue = habitPriority,
            HabitDoneCount = habitDoneCount.toInt()
        )
    }

    fun fillEditorForm(habitEditorFragment: HabitEditorFragment, habitInfo: HabitInfo?){
        if (habitInfo == null)
            return

        habitEditorFragment.editor_habit_name.setText(habitInfo.HabitName)
        habitEditorFragment.editor_habit_description.setText(habitInfo.HabitDescription)
        habitEditorFragment.editor_habit_done_count.setText(habitInfo.HabitDoneCount.toString())
        habitEditorFragment.editor_habit_periodicity.setText(habitInfo.HabitPeriodicity.toString())

        when (habitInfo.HabitPriorityValue){
            HabitPriority.Medium -> habitEditorFragment.editor_habit_priority.setSelection(1)
            HabitPriority.High -> habitEditorFragment.editor_habit_priority.setSelection(2)
            else -> habitEditorFragment.editor_habit_priority.setSelection(0)
        }
    }
}