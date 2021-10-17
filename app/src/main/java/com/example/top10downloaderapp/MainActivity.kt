package com.example.top10downloaderapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var rvQuestion: RecyclerView
    lateinit var btn: Button
    lateinit var tv: TextView
    var reTopAPP = mutableListOf<TopAPP>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reTopAPP = findViewById(R.id.rvMain)
        btn = findViewById(R.id.bt1)
        tv = findViewById(R.id.tv)

        btn.setOnClickListener {
            FetchRecentTopAPP().execute()
        }

    }

    private inner class FetchRecentTopAPP : AsyncTask<Void, Void, MutableList<TopAPP>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<TopAPP> {
            val url = URL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
            val urlConnection = url.openConnection() as HttpURLConnection
            reTopAPP = urlConnection.getInputStream()?.let {
                parser.parse(it)
            } as MutableList<TopAPP>
            return reTopAPP
        }
        override fun onPostExecute(result: MutableList<TopAPP>?) {
            super.onPostExecute(result)
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, reTopAPP)
            rvQuestion.adapter = myAdapter(result)
            rvQuestion.layoutManager = LinearLayoutManager(applicationContext)
        }

    }
}