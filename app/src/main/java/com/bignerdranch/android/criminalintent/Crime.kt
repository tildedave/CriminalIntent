package com.bignerdranch.android.criminalintent

import java.util.Date
import java.util.UUID

class Crime {
    val mId: UUID = UUID.randomUUID()
    val mTitle: String? = null
    val date: Date = Date()
    var solved: Boolean = false
}
