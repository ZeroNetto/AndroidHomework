import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import task.habits.HabitInfo
import task.habits.IOnRecyclerViewElementClickListener
import task.habits.R

class ListElementHolder(
    override val containerView: View,
    val recyclerViewElementClickListener: IOnRecyclerViewElementClickListener): RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {
    var Id: Int = 1
    var habitName = itemView.findViewById<TextView>(R.id.listElement_habit_name_value)
    var habitDescription = itemView.findViewById<TextView>(R.id.listElement_habit_description_value)
    var habitPriority = itemView.findViewById<TextView>(R.id.listElement_habit_priority_value)
    var habitPeriodicity = itemView.findViewById<TextView>(R.id.listElement_habit_periodicity_value)
    var habitDoneCount = itemView.findViewById<TextView>(R.id.listElement_habit_done_count_value)

    init{
        containerView.setOnClickListener(this)
    }

    fun bind(habitInfo: HabitInfo, position: Int){
        Id = position
        habitName.text = habitInfo.HabitName
        habitDescription.text = habitInfo.HabitDescription
        habitPriority.text = habitInfo.HabitPriorityValue.toString()
        habitPeriodicity.text = habitInfo.HabitPeriodicity.toString()
        habitDoneCount.text = habitInfo.HabitDoneCount.toString()
    }

    override fun onClick(view: View?) {
        recyclerViewElementClickListener.onRecyclerViewElementClick(view, Id)
    }
}