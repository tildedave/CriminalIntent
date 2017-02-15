package com.bignerdranch.android.criminalintent

import java.util.Date
import java.util.UUID

data class Crime(val title: String?, var solved: Boolean) {
    val uuid: UUID = UUID.randomUUID()
    val date: Date = Date()
}
