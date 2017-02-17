package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val VERSION = 1
private val DATABASE_NAME = "crimeBase.db"

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        // nothing now
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // nothing now
    }
}