package task.habits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class HabitsListActivity : AppCompatActivity(), HabitsListFragment.IHabitsListListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val viewPagerFragment = ViewPagerFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.view_pager_placeholder, viewPagerFragment)
                .commit()
        }
    }

    override fun updateHabit(actionType: Int, position: Int, habitInfo: HabitInfo?){
        val intent = Intent(this, HabitEditorActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("actionType", actionType)
        if (actionType == 2)
            intent.putExtra("habitInfo", habitInfo)
        startActivityForResult(intent, actionType)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

        val actionType = data?.getIntExtra("actionType", 1)
        val habitInfo = HabitInfoHelper().getHabitInfoFromIntent(data) ?: return
        val viewPagerFragment = supportFragmentManager.findFragmentById(R.id.view_pager_placeholder) as ViewPagerFragment
        val viewPager = viewPagerFragment.view?.findViewById<ViewPager2>(R.id.habitsTypesPagerViewPager)
        val viewPagerAdapter = viewPager?.adapter as IHabitTypeAdapter
        val currentFragment = when (viewPagerFragment.currentPosition){
            1 -> viewPagerAdapter.getBadHabitsList()
            else -> viewPagerAdapter.getGoodHabitsList()
        }
        //val habitTypePageActive = habitsListFragment.

        if (actionType == 1) {
            currentFragment.habitsList.add(habitInfo)

            currentFragment.adapter.notifyItemInserted(currentFragment.habitsList.size - 1)
        }
        if(actionType == 2){
            val id = data.getIntExtra("position", 0)
            currentFragment.habitsList[id] = habitInfo

            currentFragment.adapter.notifyItemChanged(id)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
