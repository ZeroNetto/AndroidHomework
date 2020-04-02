import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import task.habits.HabitInfo
import task.habits.IOnRecyclerViewElementClickListener
import task.habits.R

class ListElementAdapter(private val habitsInfo: List<HabitInfo>, val recyclerViewElementClickListener: IOnRecyclerViewElementClickListener):
    RecyclerView.Adapter<ListElementHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListElementHolder{
        val inflater = LayoutInflater.from(parent.context)

        return ListElementHolder(inflater.inflate(R.layout.listelement_habit_description, parent, false), recyclerViewElementClickListener)
    }

    override fun getItemCount(): Int = habitsInfo.size

    override fun onBindViewHolder(holder: ListElementHolder, position: Int){
        holder.bind(habitsInfo[position], position)
    }
}