package com.ajijul.givemeforcast.ui.main.forecast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ajijul.givemeforcast.models.forecast.ThreeHoursModel
import java.util.*

class PagerAdapter(
    fm: FragmentManager,
    private val threeHoursModels: Map<String, List<ThreeHoursModel>>
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return ForecastPerDayReportFragment(position)
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return threeHoursModels.size
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        return ArrayList(threeHoursModels.keys).get(position)
    }

}