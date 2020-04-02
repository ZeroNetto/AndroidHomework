package task.habits

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HabitTypePagerAdapter2(val activity: AppCompatActivity): FragmentStateAdapter(activity), IHabitTypeAdapter {
    val badHabitsListFragment = HabitsListFragment.newInstance(1, mutableListOf())
    val goodHabitsListFragment = HabitsListFragment.newInstance(2, mutableListOf())


    override fun createFragment(position: Int): Fragment = when(position){
        1 -> badHabitsListFragment
        else -> goodHabitsListFragment
    }

    override fun getItemCount(): Int = 2

    override fun getBadHabitsList(): HabitsListFragment = badHabitsListFragment
    override fun getGoodHabitsList(): HabitsListFragment = goodHabitsListFragment
}

interface IHabitTypeAdapter{
    fun getBadHabitsList(): HabitsListFragment
    fun getGoodHabitsList(): HabitsListFragment
}