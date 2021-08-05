package com.android.kbipapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    lateinit var tbSurname : EditText
    lateinit var tbGroup : EditText
    lateinit var tbDate : EditText

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)
        tbDate = view.findViewById(R.id.birthdate)
        tbGroup = view.findViewById(R.id.group)
        tbSurname = view.findViewById(R.id.surname)
        val preferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)

        tbSurname.setText(preferences.getString("surname", "").toString())
        tbGroup.setText(preferences.getString("group", "").toString())
        tbDate.setText(preferences.getString("birthdate", "").toString())

        tbDate.setOnClickListener {
            val picker = DatePickerDialog(it.context)
            picker.setOnDateSetListener { _, year, month, dayOfMonth ->  tbDate.setText("$dayOfMonth.$month.$year")}
            picker.show()
        }

        val saveButton : Button = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {

            val editor = preferences.edit()

            editor.putString("surname", tbSurname.text.toString())
            editor.putString("group", tbGroup.text.toString().lowercase())
            editor.putString("birthdate", tbDate.text.toString())

            editor.apply()
            editor.commit()

            Toast.makeText(context, R.string.toast_settings_saved, Toast.LENGTH_SHORT).show()
        }
        return view
    }


}