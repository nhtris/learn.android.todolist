package com.trinh.todolist3

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var item: EditText
    lateinit var add: Button
    lateinit var listView: ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.list)

        itemList = fileHelper.readData(this)

        var arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            itemList
        )

        listView.adapter = arrayAdapter

        add.setOnClickListener {
            itemList.add(item.text.toString())
            item.setText("")
            fileHelper.writeData(itemList, this)
            arrayAdapter.notifyDataSetChanged()
        }


        listView.setOnItemClickListener { adapterView, view, position, l ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item ?")
            alert.setCancelable(false)
            alert.setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })

            alert.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialogInterface, i ->

                    Log.d("MainActivity", position.toString())

                itemList.removeAt(position)
                fileHelper.writeData(itemList, this)
                arrayAdapter.notifyDataSetChanged()
            })

            alert.create().show()
        }

    }
}