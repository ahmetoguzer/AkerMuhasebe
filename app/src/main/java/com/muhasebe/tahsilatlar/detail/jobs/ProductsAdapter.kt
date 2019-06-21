package com.muhasebe.tahsilatlar.detail.jobs

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhasebe.tahsilatlar.R
import kotlinx.android.synthetic.main.row_tahsilat.view.*
import java.util.ArrayList

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class ProductsAdapter (private val mList : ArrayList<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_tahsilat,parent,false)
        return ProductsViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = mList[position]
        holder.itemView.txtProductName.text = product.productName
        holder.itemView.txtProductUnitPrice.text ="Birim Fiyatı : ${product.productUnitPrice} TL"
        holder.itemView.txtProductCount.text = "${product.productTotalCount} Adet"
        holder.itemView.txtAddedDate.text = product.mDate
        val total = product.productUnitPrice.toFloat()* product.productTotalCount.toFloat()
        holder.itemView.txtTotalAmount.text = "Toplam Tutarı : ${total.toString().split(".")[0]} TL"
        holder.itemView.setOnLongClickListener {
//            remove(company)
            return@setOnLongClickListener true
        }
    }

    class ProductsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}