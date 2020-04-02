package task.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment

class HabitEditorFragment : Fragment() {
    companion object {
        fun newInstance(position: Int, actionType: Int, habitInfo: HabitInfo? = null): HabitEditorFragment{
            val newFragment = HabitEditorFragment()
            val bundle = Bundle()

            bundle.putInt("position", position)
            bundle.putInt("actionType", actionType)
            if (actionType == 2)
                bundle.putSerializable("habitInfo", habitInfo)

            newFragment.arguments = bundle
            return newFragment
        }
    }

    private val priorities = listOf("низкий", "средний", "высокий")
    private var actionType = 1
    private var position = -1
    private var habitInfo: HabitInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            actionType = it.getInt("actionType")
            position = it.getInt("position")
        }

        if (actionType == 2)
            habitInfo = arguments?.getSerializable("habitInfo") as HabitInfo?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.habit_editor_container, container, false)

        val spinner = view?.findViewById<Spinner>(R.id.editor_habit_priority)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        if (context is IHabitEditorListener){
            val listener = context as IHabitEditorListener
            val saveButton = view?.findViewById<Button>(R.id.button_save_habit)
            saveButton?.setOnClickListener { listener.finishActivity(position, actionType, this) }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (actionType == 2) {
            HabitInfoHelper().fillEditorForm(this, habitInfo)
        }
    }

    interface IHabitEditorListener{
        fun finishActivity(position: Int = 0, actionType: Int = 1, habitEditorFragment: HabitEditorFragment)
    }
}
