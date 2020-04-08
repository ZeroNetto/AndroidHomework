package task.habits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    var currentPosition = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.habitsTypesPagerViewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.habitsTypesPagerTabLayout)
        activity?.let { activity ->
            if (activity is AppCompatActivity)
                viewPager.adapter = HabitTypePagerAdapter2(activity)
            TabLayoutMediator(tabLayout, viewPager ) { tab, position ->
                tab.text = when (position) {
                    1 -> "Плохие"
                    else -> "Хорошие"
                }
            }.attach()
        }

        setFragmentForPosition(viewPager)
    }

    private fun setFragmentForPosition(viewPager: ViewPager2){
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)

                currentPosition = position
            }
        })
    }
}
