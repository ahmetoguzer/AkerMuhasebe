package com.muhasebe.tahsilatlar.main

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhasebe.tahsilatlar.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val mList :ArrayList<Company> = ArrayList<Company>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvCustomers.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Customers")
//        myRef.setValue(getCompanies())

        myRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (item : DataSnapshot  in dataSnapshot.children ){
                    val data = item.getValue(Company::class.java)
                    if (data != null) {
                        mList.add(data)
                    }
                    rvCustomers.adapter = CompanyAdapter(mList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })


    }


    private fun getCompanies(): MutableList<Company> {
        val companies = mutableListOf(

            Company(
                "Sirac",
                "https://www.geartechnology.com/blog/wp-content/uploads/2016/02/google_logo_2015_by_eduard2009-d9809xo-800x500_c.png",
                "+905334320828"
            ),
            Company(
                "Abbey",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/YouTube_Logo.svg/2000px-YouTube_Logo.svg.png",
                "+905366268292"
            ),
            Company(
                "Sena Giyim",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/2000px-Apple_logo_black.svg.png",
                "+905322780282"
            ),
            Company(
                "Zorluer",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/IBM_logo.svg/1280px-IBM_logo.svg.png",
                "+905372694730"
            ),
            Company(
                "Nardanlı",
                "https://upload.wikimedia.org/wikipedia/tr/thumb/f/f2/Twitter-bird-light-bgs.png/220px-Twitter-bird-light-bgs.png",
                "+905063455045"
            ),
            Company(
                "Derya Giyim",
                "https://upload.wikimedia.org/wikipedia/tr/f/fa/Medium_logo.png",
                "+905322845715"
            ),
            Company(
                "Newpark",
                "http://pngimg.com/uploads/facebook_logos/facebook_logos_PNG19756.png",
                "+905344834444"
            ),
            Company(
                "Mys",
                "http://static.squarespace.com/static/531f2c4ee4b002f5b011bf00/t/536bdcefe4b03580f8f6bb16/1399577848961/hbosiliconvalleypiedpiperoldlogo",
                "+905337644547"
            )
//            Company(
//                "Wikipedia",
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Wikipedia_Logo_1.0.png/220px-Wikipedia_Logo_1.0.png",
//                ""
//            )
        )
        return companies
    }

}

