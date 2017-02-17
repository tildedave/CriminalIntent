package com.bignerdranch.android.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

var REQUEST_CRIME: Int = 1;
val SAVED_SUBTITLE_VISIBLE: String = "subtitle"

class CrimeListFragment : Fragment() {

    @BindView(R.id.crime_recycler_view)
    lateinit var crimeRecyclerView: RecyclerView

    var subtitleVisible : Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater?.inflate(R.layout.fragment_crime_list, container, false) as View
        ButterKnife.bind(this, v);

        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        if (savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }
        updateUI()

        return v
    }

    private var adapter: CrimeAdapter? = null

    private fun updateUI() {
        // TODO: scope singleton via context (getActivity())
        
        if (this.adapter == null) {
            this.adapter = CrimeAdapter(CrimeLab.crimes)
            crimeRecyclerView.adapter = this.adapter
        } else {
            // nothing for now
        }

        updateSubtitle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu.findItem(R.id.menu_item_show_subtitle)
        if (subtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
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
            val position = CrimeLab.crimes.indexOfFirst { it.uuid == uuid }

            when (resultCode) {
                Activity.RESULT_OK -> {
                    this.adapter!!.notifyItemChanged(position)
                }
                Activity.RESULT_CANCELED -> {
                    this.adapter!!.notifyItemRemoved(position)
                }
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_new_crime -> {
                val crime = Crime()
                CrimeLab.addCrime(crime)
                startActivityForResult(CrimePagerActivity.newIntent(activity, crime.uuid),
                        REQUEST_CRIME)

                return true
            }
            R.id.menu_item_show_subtitle -> {
                activity.invalidateOptionsMenu()
                subtitleVisible = !subtitleVisible
                updateSubtitle()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateSubtitle() {
        // This is dumb and pluralizes wrong
        val subtitle = if (subtitleVisible) {
            getString(R.string.subtitle_format, CrimeLab.crimes.size.toString())
        } else {
            null
        }

        (activity as AppCompatActivity).supportActionBar!!.subtitle = subtitle
    }
}