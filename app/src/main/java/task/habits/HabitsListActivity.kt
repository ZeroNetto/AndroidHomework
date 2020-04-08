package task.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class HabitsListActivity : AppCompatActivity(),
    HabitsListFragment.IHabitsListListener,
    HabitEditorFragment.IHabitEditorListener {
    private var viewPagerFragment: ViewPagerFragment = ViewPagerFragment()
    private var habitEditorFragment: HabitEditorFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.view_pager_placeholder, viewPagerFragment)
                .commit()
        }

        floatingButton_add_habit.setOnClickListener{ updateHabit(habitInfo = null) }
    }

    override fun updateHabit(actionType: Int, position: Int, habitInfo: HabitInfo?){
        habitEditorFragment = HabitEditorFragment.newInstance(
            position,
            actionType,
            habitInfo)

       supportFragmentManager
            .beginTransaction()
            .hide(viewPagerFragment)
            .replace(R.id.habit_editor_placeholder, habitEditorFragment!!)
            .commit()

        floatingButton_add_habit.visibility = View.GONE
    }

    override fun saveHabit(position: Int, actionType: Int, habitInfo: HabitInfo?) {
        val viewPager = viewPagerFragment.view?.findViewById<ViewPager2>(R.id.habitsTypesPagerViewPager)
        val viewPagerAdapter = viewPager?.adapter as IHabitTypeAdapter
        val currentFragment = when (viewPagerFragment.currentPosition){
            1 -> viewPagerAdapter.getBadHabitsList()
            else -> viewPagerAdapter.getGoodHabitsList()
        }

        if (actionType == 1 && habitInfo != null) {
            currentFragment.habitsList.add(habitInfo)

            currentFragment.adapter.notifyItemInserted(currentFragment.habitsList.size - 1)
        }
        if(actionType == 2 && habitInfo != null){
            currentFragment.habitsList[position] = habitInfo
            currentFragment.adapter.notifyItemChanged(position)
        }

        habitEditorFragment?.let {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }

        floatingButton_add_habit.visibility = View.VISIBLE

        supportFragmentManager
            .beginTransaction()
            .show(viewPagerFragment)
            .commit()
    }
}
