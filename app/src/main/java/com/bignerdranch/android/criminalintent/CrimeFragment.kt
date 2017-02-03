package com.bignerdranch.android.criminalintent

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

class CrimeFragment : Fragment() {
    lateinit private var crime: Crime

    @BindView(R.id.crime_title)
    lateinit var titleField: EditText

    @BindView(R.id.crime_date)
    lateinit var dateButton: Button

    @BindView(R.id.crime_solved)
    lateinit var solvedCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater?.inflate(R.layout.fragment_crime, container, false) as View
        ButterKnife.bind(this, v);

        dateButton.setText(crime.date.toString())
        dateButton.setEnabled(false);
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

        return v
    }

}