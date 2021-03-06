package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment() : Fragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        ButterKnife.bind(this);

        val fragmentManager = supportFragmentManager
        var f = fragmentManager.findFragmentById(R.id.fragment_container)

        if (f == null) {
            f = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, f).commit()
        }
    }
}
