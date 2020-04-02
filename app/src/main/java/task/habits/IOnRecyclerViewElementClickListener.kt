package task.habits

import android.view.View

interface IOnRecyclerViewElementClickListener {
    public fun onRecyclerViewElementClick(view: View?, position: Int)
}