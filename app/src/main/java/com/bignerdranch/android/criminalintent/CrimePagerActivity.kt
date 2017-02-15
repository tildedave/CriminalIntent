package com.bignerdranch.android.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

val EXTRA_CRIME_ID: String = "com.bignerdranch.android.criminalintent.crime_id"

class CrimePagerActivity : FragmentActivity() {

    @BindView(R.id.activity_crime_pager_view_pager)
    lateinit var viewPager: ViewPager

    lateinit var crimes : List<Crime>

    companion object {
        fun newIntent(context: Context, uuid: UUID): Intent {
            val intent = Intent(context, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, uuid)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)
        ButterKnife.bind(this)

        crimes = CrimeLab.crimes

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = crimes.get(position)
                return CrimeFragment.newInstance(crime.uuid)
            }

            override fun getCount(): Int {
                return crimes.size
            }
        }

        val uuid = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        viewPager.currentItem = crimes.indexOfFirst { it.uuid == uuid }
    }

}
