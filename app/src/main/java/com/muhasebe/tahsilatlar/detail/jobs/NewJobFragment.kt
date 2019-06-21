package com.muhasebe.tahsilatlar.detail.jobs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.muhasebe.tahsilatlar.R
import com.muhasebe.tahsilatlar.detail.TahsilatActivity
import com.muhasebe.tahsilatlar.detail.TahsilatActivity.Companion.mTotalAlinacak
import com.muhasebe.tahsilatlar.detail.payment.Payment
import kotlinx.android.synthetic.main.fragment_new_job.*
import kotlinx.android.synthetic.main.fragment_new_payment.*
import java.net.URLEncoder
import java.text.DateFormat
import java.util.*
import java.text.SimpleDateFormat


/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class NewJobFragment : Fragment() {

    var name :String =""

    companion object {
        fun newInstance(name: String): NewJobFragment {
            val args = Bundle()
            args.putString("NewJobFragment", name)
            val fragment = NewJobFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments?.getString("NewJobFragment")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Jobs")

        btnAdd.setOnClickListener{
            val currentTimeWithDate = DateFormat.getDateTimeInstance().format(Date())
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MMM-yyyy")
            val currentTime = df.format(c)

            var strAlınan : String = if(txtTotalAmount.text!!.isNotEmpty()){ txtTotalAmount.text.toString() }else{ "0" }
            val product = Product(txtProductName.text.toString(), txtProductUnit.text.toString().replace(",","."), txtProductTotalCount.text.toString(),currentTime.toString(), strAlınan)

            myRef.child(name).child(currentTimeWithDate.toString()).setValue(product).addOnCompleteListener {
                mTotalAlinacak = 0f

                Toast.makeText(activity, "Yeni iş Eklendi", Toast.LENGTH_LONG).show();
                if (txtTotalAmount.text.toString() != "0" && txtTotalAmount.text!!.isNotEmpty()){
                    val mRef = database.getReference("Payments")
                    val payment= Payment(txtTotalAmount.text.toString(),currentTime)
                    mRef.child(name).child(currentTimeWithDate.toString()).setValue(payment)
                    sendWhatsApppMessage()
                }
                txtProductName.text = null
                txtProductUnit.text = null
                txtTotalAmount.text = null
                txtProductTotalCount.text = null
            }


        }
    }

    fun sendWhatsApppMessage(){
        try {
            val sendMsg = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=${TahsilatActivity.strUser!!.phoneNumber}" + "&text=" + URLEncoder.encode(
                "${txtTotalAmount.text} TL ödemeniz Ali Erginler tarafından alınmıştır.",
                "UTF-8"
            )
            sendMsg.setPackage("com.whatsapp")
            sendMsg.data = Uri.parse(url)
            if (sendMsg.resolveActivity(activity!!.packageManager) != null) {
                startActivity(sendMsg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}