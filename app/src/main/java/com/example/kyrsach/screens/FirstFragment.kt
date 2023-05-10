package com.example.kyrsach.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.kyrsach.MAIN
import com.example.kyrsach.R
import com.example.kyrsach.databinding.FragmentFirstBinding
import com.example.kyrsach.db.MyDbManager
import com.example.kyrsach.db.Price
import com.example.kyrsach.db.Use
import java.text.SimpleDateFormat
import java.util.*

class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding
    lateinit var myDbManager: MyDbManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        myDbManager = MyDbManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnList.setOnClickListener{
            MAIN.navController.navigate(R.id.action_firstFragment_to_listFragment)
        }
        binding.btnStat.setOnClickListener {
            MAIN.navController.navigate(R.id.action_firstFragment_to_threeFragment)
        }
        binding.btnPrice.setOnClickListener {
            MAIN.navController.navigate(R.id.action_firstFragment_to_priceFragment2)
        }
        showDateTime(binding.editTextTimeStart)
        showDateTime(binding.editTextTimeFinish)

        val materialSpinner = binding.spinner
        val materials = arrayOf("3мм, 3/4", "3мм, 4/4", "4мм, 3/4", "4мм, 4/4")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, materials)
        materialSpinner.adapter = adapter

        binding.btnAdd.setOnClickListener {
            onClickAdd()
        }
    }

    fun onClickAdd(){
        val FIO = binding.editTextFIO.text.toString()
        val timeStart = binding.editTextTimeStart.text.toString()
        val timeFinish = binding.editTextTimeFinish.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val product = binding.editTextProduct.text.toString()
        val count = binding.editTextCount.text.toString()
        if(FIO.isBlank() || timeStart.isBlank() || timeFinish.isBlank() ||
            phone.isBlank() || product.isBlank() || count.isBlank()){
                Toast.makeText(context, "Не все поля заполнены!", Toast.LENGTH_SHORT).show()
//                var price = Price(0, "3мм, 3/4", "280")
//                println(price)
//                myDbManager.insertToDbPrice(price)

        }else{
            myDbManager.openDb()
            val item = binding.spinner.selectedItem.toString()
            val use = Use(0, binding.editTextFIO.text.toString(), binding.editTextTimeStart.text.toString(), binding.editTextTimeFinish.text.toString(),
                binding.editTextPhone.text.toString(), binding.editTextProduct.text.toString(), binding.editTextCount.text.toString().toInt(), item)
            myDbManager.insertToDb(use)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun showDateTime(editText: EditText) {
        val editTextDate = editText//binding.editTextTimeStart
        editTextDate.keyListener = null

        editTextDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val currentDate = Calendar.getInstance()
                val year = currentDate.get(Calendar.YEAR)
                val month = currentDate.get(Calendar.MONTH)
                val day = currentDate.get(Calendar.DAY_OF_MONTH)
                val hour = currentDate.get(Calendar.HOUR_OF_DAY)
                val minute = currentDate.get(Calendar.MINUTE)
                val dateTimeListener =
                    DatePickerDialog.OnDateSetListener { _, selectedYear, monthOfYear, dayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(Calendar.YEAR, selectedYear)
                        selectedDate.set(Calendar.MONTH, monthOfYear)
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        val timePickerDialog = TimePickerDialog(
                            requireContext(),
                            { _, selectedHour, selectedMinute ->
                                selectedDate.set(Calendar.HOUR_OF_DAY, selectedHour)
                                selectedDate.set(Calendar.MINUTE, selectedMinute)
                                val selectedDateTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm",
                                    Locale.getDefault()
                                ).format(selectedDate.time)
                                editText.setText(selectedDateTime)
                                //binding.editTextTimeStart.setText(selectedDateTime)
                            },
                            hour,
                            minute,
                            true
                        )
                        timePickerDialog.show()
                    }
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    dateTimeListener,
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }
}