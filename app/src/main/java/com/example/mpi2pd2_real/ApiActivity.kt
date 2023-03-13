package com.example.mpi2pd2_real

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_api, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.reader_but -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    class ApiActivity : AppCompatActivity() {

        private lateinit var listView: ListView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_api)

            listView = findViewById(R.id.list_view)

            // Make API request
            val queue = Volley.newRequestQueue(this)
            val url = "https://jsonplaceholder.typicode.com/todos"
            val request = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Convert JSON array to list of strings
                    val data = mutableListOf<String>()
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i)
                        data.add(item.getString("title"))
                    }

                    // Display data in ListView
                    val adapter = MyAdapter(this, data)
                    listView.adapter = adapter
                },
                { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                })

            queue.add(request)
        }
    }
    class MyAdapter(context: Context, private val data: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            }

            val item = getItem(position)
            view?.findViewById<TextView>(android.R.id.text1)?.text = item

            return view!!
        }
    }

}