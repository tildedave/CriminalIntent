package com.bignerdranch.android.criminalintent

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.DatePicker
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {
    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    @BindView(R.id.dialog_date_date_picker)
    lateinit var datePicker : DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)
        ButterKnife.bind(this, v)

        val date = arguments.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null)

        return AlertDialog.Builder(activity)
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, { dialogInterface, i ->
                    val selectedDate = GregorianCalendar(datePicker.year, datePicker.month,
                            datePicker.dayOfMonth).time
                    sendResult(Activity.RESULT_OK, selectedDate)
                })
                .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        targetFragment.onActivityResult(targetRequestCode, resultCode, intent)
    }
}