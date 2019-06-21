package com.muhasebe.tahsilatlar.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.muhasebe.tahsilatlar.detail.jobs.JobsFragment
import com.muhasebe.tahsilatlar.detail.jobs.NewJobFragment
import com.muhasebe.tahsilatlar.detail.payment.NewPaymentFragment
import com.muhasebe.tahsilatlar.detail.payment.PaymentsFragment

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class PagerAdapter(fm: FragmentManager?, tabCount: Int, productName: String) : FragmentStatePagerAdapter(fm) {

    internal var mNumOfTabs: Int = tabCount
    val productName :String = productName

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                return JobsFragment.newInstance(productName)
            }
            1 -> {
                return PaymentsFragment.newInstance(productName)
            }
            2 -> {
            return NewJobFragment.newInstance(productName)
            }
            3 -> {
            return NewPaymentFragment.newInstance(productName)
        }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}