package com.bignerdranch.android.criminalintent

import java.util.*

object CrimeLab {
    val crimes : ArrayList<Crime> = ArrayList<Crime>();

    init {
        (1..100).forEach { i ->
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
}