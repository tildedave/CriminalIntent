package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import butterknife.ButterKnife

class CrimeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime)
        ButterKnife.bind(this);

        val fragmentManager = supportFragmentManager
        var f = fragmentManager.findFragmentById(R.id.fragment_container)

        if (f == null) {
            f = CrimeFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, f).commit()
        }
    }
}
