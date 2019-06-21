package com.muhasebe.tahsilatlar.detail.payment

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
import kotlinx.android.synthetic.main.fragment_new_payment.*
import java.net.URLEncoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ahmet Oguz Er on 17.06.2019
 * Copyright (c) 2019 M-GEN Digital Agency & Future Planning Center . All rights reserved.
 */
class NewPaymentFragment : Fragment() {
    var name :String =""

    companion object {
        fun newInstance(name: String): NewPaymentFragment {
            val args = Bundle()
            args.putString("NewPaymentFragment", name)
            val fragment = NewPaymentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments?.getString("NewPaymentFragment")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPaymentTake.setOnClickListener{
            if(txtPaymentAmount.text.isNotEmpty()){
                val currentTimeWithDate = DateFormat.getDateTimeInstance().format(Date())
                val c = Calendar.getInstance().time
                val df = SimpleDateFormat("dd-MMM-yyyy")
                val currentTime = df.format(c)
                val database = FirebaseDatabase.getInstance()
                val mRef = database.getReference("Payments")
                val payment= Payment(txtPaymentAmount.text.toString(),currentTime)
                mRef.child(name).child(currentTimeWithDate.toString()).setValue(payment).addOnCompleteListener {
                    sendWhatsApppMessage()
                    txtPaymentAmount.text=null
                    Toast.makeText(activity, "Tahsilat alındı", Toast.LENGTH_LONG).show()

                }
            }

        }
    }

    fun sendWhatsApppMessage(){
                    try {
                val sendMsg = Intent(Intent.ACTION_VIEW)
                val url = "https://api.whatsapp.com/send?phone=${TahsilatActivity.strUser!!.phoneNumber}" + "&text=" + URLEncoder.encode(
                    "${txtPaymentAmount.text} TL ödemeniz Ali Erginler tarafından alınmıştır.",
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