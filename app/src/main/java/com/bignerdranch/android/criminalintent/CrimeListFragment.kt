package com.bignerdranch.android.criminalintent

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

var REQUEST_CRIME: Int = 1;

class CrimeListFragment : Fragment() {

    @BindView(R.id.crime_recycler_view)
    lateinit var crimeRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater?.inflate(R.layout.fragment_crime_list, container, false) as View
        ButterKnife.bind(this, v);

        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        updateUI()

        return v
    }

    private var adapter: CrimeAdapter? = null

    private fun updateUI(uuid: UUID? = null) {
        // TODO: scope singleton via context (getActivity())
        
        if (this.adapter == null) {
            this.adapter = CrimeAdapter(CrimeLab.crimes)
            crimeRecyclerView.adapter = this.adapter
        } else if (uuid == null) {
            System.out.println("EVERYTHING CHANGED :( THIS IS BAD PERFORMANCE")
            this.adapter!!.notifyDataSetChanged()
        } else {
            val position = CrimeLab.crimes.indexOfFirst { it.uuid == uuid }
            this.adapter!!.notifyItemChanged(position)
        }
    }

    inner class CrimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.list_item_crime_title_text_view)
        lateinit var titleTextView: TextView

        @BindView(R.id.list_item_crime_date_text_view)
        lateinit var dateTextView: TextView

        @BindView(R.id.list_item_crime_solved_check_box)
        lateinit var solvedCheckBox: CheckBox

        lateinit var crime: Crime

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bindCrime(crime: Crime) {
            this.crime = crime

            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedCheckBox.isChecked = crime.solved
            solvedCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                crime.solved = isChecked
            }
        }

        override fun onClick(v: View?) {
            startActivityForResult(CrimePagerActivity.newIntent(activity, crime.uuid), REQUEST_CRIME)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CRIME) {
            val uuid = data!!.getSerializableExtra(EXTRA_CRIME_ID) as UUID
            updateUI(uuid)
        }
    }

    inner class CrimeAdapter(val crimes: List<Crime>): RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            var layoutInflater = LayoutInflater.from(activity)
            val v = layoutInflater.inflate(R.layout.list_item_crime, parent, false)

            return CrimeHolder(v)
        }

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            val crime = crimes[position]
            holder?.bindCrime(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }
    }
}