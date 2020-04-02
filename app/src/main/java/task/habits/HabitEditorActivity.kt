package task.habits

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HabitEditorActivity : AppCompatActivity(), HabitEditorFragment.IHabitEditorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editor)

        val position = intent.getIntExtra("position", 0)
        val actionType = intent.getIntExtra("actionType", 0)
        var habitInfo: HabitInfo? = null
        if (actionType == 2)
            habitInfo = intent.getSerializableExtra("habitInfo") as HabitInfo?

        if (savedInstanceState == null){
            val habitEditor = HabitEditorFragment.newInstance(position, actionType, habitInfo)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.habit_editor_placeholder, habitEditor)
                .commit()
        }
    }

    override fun finishActivity(position: Int, actionType: Int, habitEditorFragment: HabitEditorFragment){
        val intent = Intent(this, HabitEditorFragment::class.java)
        HabitInfoHelper().fillIntentWithFormEditorInfo(habitEditorFragment, intent)
        intent.putExtra("actionType", actionType)

        if(actionType == 2)
            intent.putExtra("position", position)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
