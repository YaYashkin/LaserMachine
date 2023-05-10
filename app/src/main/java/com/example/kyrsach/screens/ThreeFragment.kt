package com.example.kyrsach.screens

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.kyrsach.MAIN
import com.example.kyrsach.R
import com.example.kyrsach.databinding.FragmentThreeBinding
import com.example.kyrsach.db.MyDbManager
import com.example.kyrsach.db.Price

class ThreeFragment() : Fragment() {

    lateinit var binding: FragmentThreeBinding
    lateinit var myDbManager: MyDbManager
    lateinit var list: MutableList<Price>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreeBinding.inflate(layoutInflater, container, false)
        myDbManager = MyDbManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            MAIN.navController.navigate(R.id.action_threeFragment_to_firstFragment)
        }

        myDbManager.openDb()
        list = myDbManager.readDbDataPrice()
        binding.f3mm34.text = list[0].PRICE
        binding.f3mm44.text = list[1].PRICE
        binding.f4mm34.text = list[2].PRICE
        binding.f4mm44.text = list[3].PRICE

        val result = myDbManager.allPrice()
        binding.itemPotrFan.text = result.toString()

        val res3mm34 = myDbManager.getMaterialPrice("3мм, 3/4")
        val res3mm44 = myDbManager.getMaterialPrice("3мм, 4/4")
        val res4mm34 = myDbManager.getMaterialPrice("4мм, 3/4")
        val res4mm44 = myDbManager.getMaterialPrice("4мм, 4/4")

        val res = (convertPrice(res3mm34, "3мм, 3/4", list) + convertPrice(res3mm44, "3мм, 4/4", list) +
                convertPrice(res4mm34, "4мм, 3/4", list) + convertPrice(res4mm44, "4мм, 4/4", list)).toString()

        binding.f3mm34.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактрирование цены")
            val dialogLayout = View.inflate(context, R.layout.edit_price, null)
            builder.setView(dialogLayout)

            val oldPrice = binding.f3mm34.text.toString()
            dialogLayout.findViewById<EditText>(R.id.editTextPrice).setText(oldPrice)

            builder.setPositiveButton("OK"){ _, i ->
                try {
                    val newPrice = dialogLayout.findViewById<EditText>(R.id.editTextPrice).text.toString()
                    if (newPrice.isNotEmpty()){
                        val myDbManager = MyDbManager(requireContext())
                        val price = Price(1, "3мм, 3/4", newPrice)
                        myDbManager.openDb()
                        myDbManager.updatePrice(price)
                        refresh(myDbManager.readDbDataPrice())
                        refreshAllPrice(res)
                    }else{
                        Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: java.lang.Exception){
                    Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Отмена") { _, i ->
                Toast.makeText(this.context, "Отмена", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        binding.f3mm44.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактрирование цены")
            val dialogLayout = View.inflate(context, R.layout.edit_price, null)
            builder.setView(dialogLayout)

            val oldPrice = binding.f3mm44.text.toString()
            dialogLayout.findViewById<EditText>(R.id.editTextPrice).setText(oldPrice)

            builder.setPositiveButton("OK"){ _, i ->
                try {
                    val newPrice = dialogLayout.findViewById<EditText>(R.id.editTextPrice).text.toString()
                    if (newPrice.isNotEmpty()){
                        val myDbManager = MyDbManager(requireContext())
                        val price = Price(2, "3мм, 4/4", newPrice)
                        myDbManager.openDb()
                        myDbManager.updatePrice(price)
                        refresh(myDbManager.readDbDataPrice())
                    }else{
                        Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: java.lang.Exception){
                    Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Отмена") { _, i ->
                Toast.makeText(this.context, "Отмена", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        binding.f4mm34.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактрирование цены")
            val dialogLayout = View.inflate(context, R.layout.edit_price, null)
            builder.setView(dialogLayout)

            val oldPrice = binding.f4mm34.text.toString()
            dialogLayout.findViewById<EditText>(R.id.editTextPrice).setText(oldPrice)

            builder.setPositiveButton("OK"){ _, i ->
                try {
                    val newPrice = dialogLayout.findViewById<EditText>(R.id.editTextPrice).text.toString()
                    if (newPrice.isNotEmpty()){
                        val myDbManager = MyDbManager(requireContext())
                        val price = Price(3, "4мм, 3/4", newPrice)
                        myDbManager.openDb()
                        myDbManager.updatePrice(price)
                        refresh(myDbManager.readDbDataPrice())
                    }else{
                        Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: java.lang.Exception){
                    Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Отмена") { _, i ->
                Toast.makeText(this.context, "Отмена", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        binding.f4mm44.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактрирование цены")
            val dialogLayout = View.inflate(context, R.layout.edit_price, null)
            builder.setView(dialogLayout)

            val oldPrice = binding.f4mm44.text.toString()
            dialogLayout.findViewById<EditText>(R.id.editTextPrice).setText(oldPrice)

            builder.setPositiveButton("OK"){ _, i ->
                try {
                    val newPrice = dialogLayout.findViewById<EditText>(R.id.editTextPrice).text.toString()
                    if (newPrice.isNotEmpty()){
                        val myDbManager = MyDbManager(requireContext())
                        val price = Price(4, "4мм, 4/4", newPrice)
                        myDbManager.openDb()
                        myDbManager.updatePrice(price)
                        refresh(myDbManager.readDbDataPrice())
                    }else{
                        Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: java.lang.Exception){
                    Toast.makeText(this.context, "Заполните поле", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Отмена") { _, i ->
                Toast.makeText(this.context, "Отмена", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        binding.itemElFan.text = res

        binding.itemPotrPower.text = myDbManager.getTotalUsageTime().toString()
        binding.itemPowerStoim.text = String.format("%.2f", ((myDbManager.getTotalUsageTime() / 60) * 0.05 * 1.23)).toString()
    }

    fun refresh(newData: MutableList<Price>){
        list.clear()
        list.addAll(newData)
        list = myDbManager.readDbDataPrice()
        binding.f3mm34.text = list[0].PRICE
        binding.f3mm44.text = list[1].PRICE
        binding.f4mm34.text = list[2].PRICE
        binding.f4mm44.text = list[3].PRICE
    }

    fun refreshAllPrice(res: String){
        binding.itemElFan.text
    }


    fun convertPrice(amount: Int, typeFan: String, newData: MutableList<Price>): Int {
        var fan3mm34 = amount * list[0].PRICE.toInt()
        var fan3mm44 = amount * list[1].PRICE.toInt()
        var fan4mm34 = amount * list[2].PRICE.toInt()
        var fan4mm44 = amount * list[3].PRICE.toInt()
        return when (typeFan) {
            "3мм, 3/4" -> fan3mm34
            "3мм, 4/4" -> fan3mm44
            "4мм, 3/4" -> fan4mm34
            "4мм, 4/4" -> fan4mm44
            else -> 0
        }
    }
}