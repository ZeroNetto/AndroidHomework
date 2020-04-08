package task.habits

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment


class HabitEditorFragment : Fragment(){
    private val priorities = listOf("низкий", "средний", "высокий")
    var position: Int = 0
    var actionType: Int = 0
    var habitInfo: HabitInfo? = null

    companion object{

        fun newInstance(
            position: Int = 0,
            actionType: Int = 0,
            habitInfo: HabitInfo?) : HabitEditorFragment{
            val newHabitsEditor = HabitEditorFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putInt("actionType", actionType)
            if (actionType == 2)
                bundle.putSerializable("habitInfo", habitInfo)

            newHabitsEditor.arguments = bundle
            return newHabitsEditor
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habit_editor_container, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt("position", 0) ?: 0
        actionType = arguments?.getInt("actionType", 0) ?: 0
        if (actionType == 2){
            habitInfo = arguments?.getSerializable("habitInfo") as HabitInfo?
            HabitInfoHelper().fillEditorForm(this, habitInfo)
        }

        val prioritySpinner = view.findViewById<Spinner>(R.id.editor_habit_priority)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = adapter

        val saveButton = view.findViewById<Button>(R.id.button_save_habit)
        saveButton.setOnClickListener{ saveHabit(position, actionType)}
    }

    private fun saveHabit(position: Int, actionType: Int){
        val imm: InputMethodManager =
            context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        val habitInfo = HabitInfoHelper().getHabitInfoFromEditorInfo(this)

        if (context is IHabitEditorListener){
            val listener = context as IHabitEditorListener
            listener.saveHabit(position, actionType, habitInfo)
        }
    }

    interface IHabitEditorListener{
        fun saveHabit(position: Int, actionType: Int, habitInfo: HabitInfo?)
    }
}
