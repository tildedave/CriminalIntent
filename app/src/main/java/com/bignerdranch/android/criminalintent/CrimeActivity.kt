package com.bignerdranch.android.criminalintent

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

val EXTRA_CRIME_ID: String = "com.bignerdranch.android.criminalintent.crime_id"

class CrimeActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val uuid = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(uuid)
    }

    companion object {
        fun newIntent(context: Context, uuid: UUID): Intent {
            val intent = Intent(context, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, uuid)

            return intent
        }
    }
}