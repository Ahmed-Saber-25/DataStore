package com.ahmedsaber.protodatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ahmedsaber.protodatastore.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.readFromProtoDataStore.observe(this, {
            Log.d("values", "onCreate: "+ it.firstName.toString()+it.lastName.toString()+it.age.toString())
            resultFromProtoDataStore.text = "Result From Proto DataStore:"+"\n\n"+"FirstName: " + it.firstName.toString() + "\n" +
                    "LastName: " + it.lastName.toString() + "\n" +
                    "Age:  " + it.age.toString() + "\n"
        })
        viewModel.readFromPrefDataStore.observe(this, {
            Log.d("values", "onCreate: "+ it[0].toString()+it[1].toString()+it[2].toString())
            resultFromPrefDataStore.text = "Result From Pref DataStore:"+"\n\n"+"FirstName: " + it[0].toString() + "\n" +
                    "LastName: " + it[1].toString() + "\n" +
                    "Age:  " + it[2].toString() + "\n"
        })
        save_proto_Btn.setOnClickListener {
            if (editTextFirstName.text.trim().isNullOrEmpty()||editTextLastName.text.trim().isNullOrEmpty()||editTextNumber.text.trim().isNullOrEmpty()) {
                showToast("check your entries")
            }
            else {
                viewModel.updateValueUsingProto(editTextFirstName.text.toString(), editTextLastName.text.toString(), editTextNumber.text.toString().toInt())
                showToast("your data saved successfully")
            }
        }
        save_pref_Btn.setOnClickListener {
            if (editTextFirstName.text.trim().isNullOrEmpty()||editTextLastName.text.trim().isNullOrEmpty()||editTextNumber.text.trim().isNullOrEmpty()) {
                showToast("check your entries")
            }
            else {
                viewModel.updateValueUsingPref(editTextFirstName.text.toString(), editTextLastName.text.toString(), editTextNumber.text.toString().toInt())
                showToast("your data saved successfully")
            }
        }
    }

    private fun showToast(message:String) {
        Toast.makeText(this,message , Toast.LENGTH_SHORT).show()
    }
}