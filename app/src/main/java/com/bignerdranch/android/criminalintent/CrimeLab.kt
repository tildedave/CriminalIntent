package com.bignerdranch.android.criminalintent

import android.content.Context
import android.support.v4.app.FragmentActivity
import java.util.*

object CrimeLab {
    val crimes : ArrayList<Crime> = ArrayList<Crime>();

    init {
        (1..2).forEach { i ->
            val crime = Crime("Crime " + i, i % 2 == 0)
            crimes.add(crime)
        }
    }

    fun getCrime(uuid: UUID): Crime? {
        crimes.forEach { crime ->
            if (crime.uuid == uuid) {
                return crime
            }
        }

        return null
    }

    fun addCrime(c: Crime) {
        crimes.add(c)
    }
}