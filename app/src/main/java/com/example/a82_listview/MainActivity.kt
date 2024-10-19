package com.example.a82_listview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val notes: MutableList<User> = mutableListOf()

    private lateinit var toolbarMain: androidx.appcompat.widget.Toolbar

    private lateinit var nameET: EditText
    private lateinit var ageET: EditText

    private lateinit var saveBTN: Button
    private lateinit var listViewLV: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Каталог пользователей"
        toolbarMain.subtitle = "версия 1"
        toolbarMain.setLogo(R.drawable.baseline_format_list_bulleted_add_24)

        listViewLV = findViewById(R.id.listViewLV)
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes)
        listViewLV.adapter = adapter

        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() || ageET.text.isEmpty()) {
                return@setOnClickListener
            }
            val user = User(nameET.text.toString(), ageET.text.toString().toInt())
            notes.add(user)
            adapter.notifyDataSetChanged()
            nameET.text.clear()
            ageET.text.clear()
        }

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, v, position, id ->
                val note = adapter.getItem(position)
                adapter.remove(note)
                Toast.makeText(this, "Пользователь удалён: $note", Toast.LENGTH_LONG).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}