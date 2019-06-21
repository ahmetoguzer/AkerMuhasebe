package com.muhasebe.tahsilatlar.detail.payment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhasebe.tahsilatlar.R
import kotlinx.android.synthetic.main.row_payment.view.*
import java.util.ArrayList

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class PaymentsAdapter (private val mList : ArrayList<Payment>) : RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_payment,parent,false)
        return PaymentsViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        val payment = mList[position]
        holder.itemView.txtPaymentAmount.text = "${payment.paymentAmount} TL"
        holder.itemView.txtPaymentDate.text = payment.paymentDate
    }

    class PaymentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}