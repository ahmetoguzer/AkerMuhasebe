package com.muhasebe.tahsilatlar.detail.payment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhasebe.tahsilatlar.R
import com.muhasebe.tahsilatlar.detail.jobs.JobsFragment
import com.muhasebe.tahsilatlar.detail.jobs.Product
import com.muhasebe.tahsilatlar.detail.jobs.ProductsAdapter
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_payments.*
import java.util.ArrayList

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class PaymentsFragment :Fragment() {
    var name :String =""
    val mList : ArrayList<Payment> = ArrayList<Payment>()
    var mView : View? = null

    companion object {
        fun newInstance(name: String): PaymentsFragment {
            val args = Bundle()
            args.putString("PaymentsFragment", name)
            val fragment = PaymentsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments?.getString("PaymentsFragment")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_payments, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Payments")
//        myRef.setValue(getCompanies())

        myRef.child(name).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item : DataSnapshot in dataSnapshot.children ){
                    val data = item.getValue(Payment::class.java)
                    if (data != null) {
                        mList.add(data)
                    }
                }
                var rvPayments = mView!!.findViewById(R.id.rvPayments) as RecyclerView
                rvPayments.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
                mList.reverse()
                rvPayments.adapter = PaymentsAdapter(mList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }
}