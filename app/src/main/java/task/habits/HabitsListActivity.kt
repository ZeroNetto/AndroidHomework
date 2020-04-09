package task.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class HabitsListActivity : AppCompatActivity(),
    HabitsListFragment.IHabitsListListener,
    HabitEditorFragment.IHabitEditorListener,
    NavigationView.OnNavigationItemSelectedListener{
    private var viewPagerFragment: ViewPagerFragment = ViewPagerFragment()
    private var habitEditorFragment: HabitEditorFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerToggle = ActionBarDrawerToggle(this,
            navigationDrawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawerToggle.isDrawerIndicatorEnabled = true
        navigationDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigationDrawer.setNavigationItemSelectedListener(this)

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
        navigationDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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
        navigationDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        supportFragmentManager
            .beginTransaction()
            .show(viewPagerFragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_item_appInfo ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.view_pager_placeholder, app_info_fragment())
                    .commit()
                floatingButton_add_habit.visibility = View.GONE
            }
            R.id.menu_item_mainPage ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.view_pager_placeholder, viewPagerFragment)
                    .commit()
                floatingButton_add_habit.visibility = View.VISIBLE
            }
            else -> return false
        }

        return true
    }

    override fun onBackPressed() {
        if (navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            navigationDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
