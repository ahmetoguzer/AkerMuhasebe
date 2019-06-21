package com.muhasebe.tahsilatlar.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.muhasebe.tahsilatlar.R
import com.muhasebe.tahsilatlar.detail.TahsilatActivity
import kotlinx.android.synthetic.main.row_customers.view.*

/**
 * Created by Ahmet Oguz Er on 14.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */

class CompanyAdapter(val companies: MutableList<Company>) : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_customers,parent,false)
        return CompanyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return companies.size
    }
    fun add(item: Company, position:Int) {
        companies.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: Company) {
        val position = companies.indexOf(item)
        companies.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        holder.itemView.txtCompanyName.text = company.name
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,"${company.name} is the best company in the world.",
            Toast.LENGTH_SHORT).show()

            val intent = Intent(holder.itemView.context, TahsilatActivity::class.java)
            intent.putExtra("keyIdentifier", company)
            holder.itemView.context.startActivity(intent)

        }
        holder.itemView.setOnLongClickListener {
            remove(company)
            return@setOnLongClickListener true
        }
    }


    class CompanyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}