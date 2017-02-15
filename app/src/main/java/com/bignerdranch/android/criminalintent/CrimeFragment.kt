package com.bignerdranch.android.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {

    private val DIALOG_DATE: String = "DialogDate"
    private val REQUEST_DATE = 0

    lateinit private var crime: Crime

    @BindView(R.id.crime_title)
    lateinit var titleField: EditText

    @BindView(R.id.crime_date)
    lateinit var dateButton: Button

    @BindView(R.id.crime_solved)
    lateinit var solvedCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uuid = arguments.getSerializable(ARG_CRIME_ID) as UUID

        crime = CrimeLab.getCrime(uuid)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater?.inflate(R.layout.fragment_crime, container, false) as View
        ButterKnife.bind(this, v);

        titleField.setText(crime.title)
        titleField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        updateDateButton()
        dateButton.setOnClickListener { v ->
            val dialog = DatePickerFragment.newInstance(crime.date)
            dialog.setTargetFragment(this, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }

        solvedCheckbox.isChecked = crime.solved
        solvedCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            crime.solved = isChecked
        }

        returnResult()

        return v
    }

    fun returnResult() {
        activity.setResult(Activity.RESULT_OK, activity.intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_DATE) {
            crime.date = data!!.getSerializableExtra(EXTRA_DATE) as Date
            updateDateButton()
        }
    }

    private fun updateDateButton() {
        dateButton.text = crime.date.toString()
    }

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args

            return fragment
        }
    }
}