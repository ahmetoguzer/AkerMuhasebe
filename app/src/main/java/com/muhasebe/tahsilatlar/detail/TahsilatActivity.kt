package com.muhasebe.tahsilatlar.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhasebe.tahsilatlar.R
import com.muhasebe.tahsilatlar.detail.jobs.Product
import com.muhasebe.tahsilatlar.detail.payment.Payment
import com.muhasebe.tahsilatlar.main.Company
import kotlinx.android.synthetic.main.activity_tahsilatlar.*
import java.lang.StringBuilder

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class TahsilatActivity : AppCompatActivity() {

    companion object {
        var mTotalAlinacak : Float = 0f
        var mTotalAlinan: Int = 0
        var strUser: Company? = null
        var isOpen: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahsilatlar)
        strUser = (intent.getSerializableExtra("keyIdentifier") as? Company)!!

        title= strUser!!.name

        tabLayout.addTab(tabLayout.newTab().setText("İşler"))
        tabLayout.addTab(tabLayout.newTab().setText("TahsilatLar"))
        tabLayout.addTab(tabLayout.newTab().setText("İş Ekle"))
        tabLayout.addTab(tabLayout.newTab().setText("Tahsilat Al"))

        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount, strUser!!.name)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val database = FirebaseDatabase.getInstance()
        val mjRef = database.getReference("Jobs")
//        myRef.setValue(getCompanies())

        mjRef.child(strUser!!.name).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item : DataSnapshot in dataSnapshot.children ){
                    val data = item.getValue(Product::class.java)
                    if (data != null) {
                        val i :String = data.productUnitPrice
                        val j :String = data.productTotalCount
                        val total = i.toFloat()*j.toFloat()
                        mTotalAlinacak += total

                    }
                }

                val str =mTotalAlinacak.toString().split(".")
                val strTotal:StringBuilder= StringBuilder(str[0])

                if(strTotal.length==4){
                    strTotal.insert(1,".")
                }else if(strTotal.length==5){
                    strTotal.insert(2,".")
                }else if(strTotal.length==6){
                    strTotal.insert(3,".")
                }
                txtTotalAlinacak.text="Toplam \nAlınacak \nTutar : "+ strTotal.toString()+" TL"
                mTotalAlinacak = 0f
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        val mpRef = database.getReference("Payments")
//        myRef.setValue(getCompanies())
        mpRef.child(strUser!!.name).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item : DataSnapshot in dataSnapshot.children ){
                    val data = item.getValue(Payment::class.java)
                    if (data != null) {
                        val i :String = data.paymentAmount
                        mTotalAlinan +=Integer.valueOf(i)

                    }
                }
                val strTotal:StringBuilder= StringBuilder(mTotalAlinan.toString())
                if(mTotalAlinan.toString().length==4){
                    strTotal.insert(1,".")
                }else if(mTotalAlinan.toString().length==5){
                    strTotal.insert(2,".")
                }else if(mTotalAlinan.toString().length==6){
                    strTotal.insert(3,".")
                }
                txtTotalAlinan.text="Toplam \nAlınan \nTutar : "+ strTotal+" TL"
                mTotalAlinan = 0
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        btnShow.setOnClickListener{
            if(isOpen){
                linearLayoutPayment.visibility = View.GONE
                val img = resources.getDrawable( R.drawable.ic_down )
                img.setBounds( 0, 0, 60, 60 )
                btnShow.setCompoundDrawables(null, null, img, null)
                isOpen =false
            }else{
                linearLayoutPayment.visibility = View.VISIBLE
                val img = resources.getDrawable( R.drawable.ic_up )
                img.setBounds( 0, 0, 60, 60 )
                btnShow.setCompoundDrawables(null, null, img, null)
                isOpen =true
            }

        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.left - scrcoords[0]
            val y = ev.rawY + v.top - scrcoords[1]

            if (x < v.left || x > v.right || y < v.top || y > v.bottom)
                hideKeyboard()
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}