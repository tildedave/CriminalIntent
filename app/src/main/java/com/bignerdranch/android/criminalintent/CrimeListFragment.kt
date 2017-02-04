package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

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

    private fun updateUI() {
        // TODO: scope via context (getActivity())
        crimeRecyclerView.adapter = CrimeAdapter(CrimeLab.crimes)
    }

    class CrimeHolder(val titleTextView: TextView) : RecyclerView.ViewHolder(titleTextView) {
    }

    inner class CrimeAdapter(val crimes: List<Crime>): RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            var layoutInflater = LayoutInflater.from(activity)
            val v = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false) as TextView

            return CrimeHolder(v)
        }

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            val crime = crimes[position]
            if (holder != null) {
                holder.titleTextView.text = crime.title
            }
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

    }
}