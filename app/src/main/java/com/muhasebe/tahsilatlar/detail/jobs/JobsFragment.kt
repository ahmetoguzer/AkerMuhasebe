package com.muhasebe.tahsilatlar.detail.jobs

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
import java.util.*

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
 class JobsFragment : Fragment() {

    var name :String =""
    val mList : ArrayList<Product> = ArrayList<Product>()
    var mView : View? = null


    companion object {
        fun newInstance(name: String): JobsFragment {
            val args = Bundle()
            args.putString("JobsFragment", name)
            val fragment = JobsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments?.getString("JobsFragment")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_jobs, container, false)
        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Jobs")
//        myRef.setValue(getCompanies())

        myRef.child(name).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item : DataSnapshot in dataSnapshot.children ){
                    val data = item.getValue(Product::class.java)
                    if (data != null) {
                        mList.add(data)
                    }
                }
                var rvProducts = mView!!.findViewById(R.id.rvProducts) as RecyclerView
                rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
                mList.reverse()
                rvProducts.adapter = ProductsAdapter(mList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })


    }
}