package task.habits

import ListElementAdapter
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HabitsListFragment : Fragment(), IOnRecyclerViewElementClickListener {
    var habitsList: MutableList<HabitInfo> = mutableListOf()
    var adapter = ListElementAdapter(habitsList, this)
    var habitType = 0
    private val fragmentContext = this

    companion object{

        fun newInstance(
                habitType: Int = 0,
                habitsList: MutableList<HabitInfo>): HabitsListFragment{
            val newHabitsList = HabitsListFragment()
            val bundle = Bundle()
            bundle.putInt("habitType", habitType)
            bundle.putSerializable("habitsList", ArrayList(habitsList))

            newHabitsList.arguments = bundle

            return newHabitsList
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.habits_list_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitType = arguments?.getInt("habitType", 0) ?: 0
        habitsList = arguments?.getSerializable("habitsList") as ArrayList<HabitInfo>

        setAdapterForHabitType()
    }

    private fun setAdapterForHabitType(){
        adapter = ListElementAdapter(habitsList, fragmentContext)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView_habits)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            DividerItemDecoration(fragmentContext.context, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onRecyclerViewElementClick(view: View?, position: Int) {
        if (context is IHabitsListListener){
            val listener = context as IHabitsListListener
            listener.updateHabit(2, position, habitsList[position])
        }
    }

    interface IHabitsListListener{
        fun updateHabit(actionType: Int = 1, position: Int = 0, habitInfo: HabitInfo?)
    }
}
