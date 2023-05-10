package com.example.kyrsach.db

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kyrsach.R
import com.example.kyrsach.screens.FirstFragment
import java.text.SimpleDateFormat
import java.util.*

class UseListAdapter(private val context: Context, private val useList: MutableList<Use>) :
    RecyclerView.Adapter<UseListAdapter.UseViewHolder>() {
    lateinit var myDbManager: MyDbManager

    class UseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val listUse: ConstraintLayout
        val dataFIO: TextView
        val dataDateStart: TextView
        val dataDateFinish: TextView
        val dataNumber: TextView
        val dataName: TextView
        val dataCount: TextView
        val dataMaterial: TextView

//        val dataPriceMaterial: TextView
//        val dataPricePower: TextView

        init {
            dataFIO = itemView.findViewById(R.id.dataFIO)
            dataDateStart = itemView.findViewById(R.id.dataDateStart)
            dataDateFinish = itemView.findViewById(R.id.dataDateFinish)
            dataNumber = itemView.findViewById(R.id.dataNumber)
            dataName = itemView.findViewById(R.id.dataName)
            dataCount = itemView.findViewById(R.id.dataCount)
            dataMaterial  = itemView.findViewById(R.id.dataMaterial)

            listUse = itemView.findViewById(R.id.recycler_view_uses)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.maket_recycler, parent, false)
        return UseViewHolder(view)
    }

    override fun onBindViewHolder(holder: UseViewHolder, position: Int) {
        holder.dataFIO.text = useList[position].FIO
        holder.dataDateStart.text = useList[position].DATE_START
        holder.dataDateFinish.text = useList[position].DATE_FINISH
        holder.dataNumber.text = useList[position].NUMBER
        holder.dataName.text = useList[position].PRODUCT
        holder.dataCount.text = useList[position].COUNT.toString()
        holder.dataMaterial.text = useList[position].MATERIAL

        holder.listUse.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактировать запись под номером ${position + 1}")
            val dialogLayout = View.inflate(context, R.layout.edit_uses_dialog, null)
            builder.setView(dialogLayout)

            dialogLayout.findViewById<EditText>(R.id.editUsesFIO).setText(useList[position].FIO)
            dialogLayout.findViewById<EditText>(R.id.editUsesTimeStart).setText(useList[position].DATE_START)
            dialogLayout.findViewById<EditText>(R.id.editUsesTimeFinish).setText(useList[position].DATE_FINISH)
            dialogLayout.findViewById<EditText>(R.id.editUsesPhone).setText(useList[position].NUMBER)
            dialogLayout.findViewById<EditText>(R.id.editTextProduct).setText(useList[position].PRODUCT)
            dialogLayout.findViewById<EditText>(R.id.editTextCount).setText(useList[position].COUNT.toString())

            val materialSpinner = dialogLayout.findViewById<Spinner>(R.id.spinner)
            val materials = arrayOf("3мм, 3/4", "3мм, 4/4", "4мм, 3/4", "4мм, 4/4")
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, materials)
            materialSpinner.adapter = adapter


            dialogLayout.findViewById<EditText>(R.id.editUsesTimeStart).setOnClickListener {
                showDateTime(dialogLayout.findViewById<EditText>(R.id.editUsesTimeStart))
            }

            dialogLayout.findViewById<EditText>(R.id.editUsesTimeFinish).setOnClickListener {
                showDateTime(dialogLayout.findViewById<EditText>(R.id.editUsesTimeFinish))
            }

            builder.setPositiveButton("ОК"){ _, i ->
                try {
                    val item = dialogLayout.findViewById<Spinner>(R.id.spinner).selectedItem.toString()
                    val dialogFio = dialogLayout.findViewById<EditText>(R.id.editUsesFIO).text.toString()
                    val dialogDateStart = dialogLayout.findViewById<EditText>(R.id.editUsesTimeStart).text.toString()
                    val dialogDateFinish = dialogLayout.findViewById<EditText>(R.id.editUsesTimeFinish).text.toString()
                    val dialogNumber = dialogLayout.findViewById<EditText>(R.id.editUsesPhone).text.toString()
                    val dialogProduct = dialogLayout.findViewById<EditText>(R.id.editTextProduct).text.toString()
                    val dialogCount = dialogLayout.findViewById<EditText>(R.id.editTextCount).text.toString().toInt()
                    println("$position\n$dialogFio\n$dialogDateStart\n$dialogDateFinish\n$dialogNumber\n$dialogProduct\n$dialogCount\n$item")
                    if(dialogFio.isBlank() && dialogDateStart.isBlank() && dialogDateFinish.isBlank() &&
                        dialogNumber.isBlank() && dialogProduct.isBlank()){
                        toastShow("Не все поля заполнены!")
                    }else{
                        val use = Use(position, dialogFio, dialogDateStart, dialogDateFinish, dialogNumber, dialogProduct, dialogCount, item)
                        val myDbManager = MyDbManager(context)
                        myDbManager.openDb()
                        myDbManager.updateUses(use)
                        refresh(myDbManager.readDbData())
                        toastShow("Изменено!")
                    }
                }catch (e: java.lang.Exception){
                    toastShow("Не все поля заполнены!")
                }
            }
            builder.setNegativeButton("Отмена") { _, i ->
                toastShow("Отмена")
            }
            builder.setNeutralButton("Удалить"){ _, i ->
                val myDbManager = MyDbManager(context)
                myDbManager.openDb()
                myDbManager.deleteUses(useList[position].ID-1)
                refresh(myDbManager.readDbData())
                toastShow("Удалено!")
            }
            builder.show()
        }
    }

    fun convertPrice(amount: Int, typeFan: String): Int {
        var fan3mm34 = amount * 280
        var fan3mm44 = amount * 400
        var fan4mm34 = amount * 335
        var fan4mm44 = amount * 500
        return when (typeFan) {
            "3мм, 3/4" -> fan3mm34
            "3мм, 4/4" -> fan3mm44
            "4мм, 3/4" -> fan4mm34
            "4мм, 4/4" -> fan4mm44
            else -> 0
        }
    }

    fun toastShow(str: String){
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return useList.size
    }

    fun refresh(newData: MutableList<Use>){
        useList.clear()
        useList.addAll(newData)
        notifyDataSetChanged()
    }

    fun showDateTime(editText: EditText) {
        val editTextDate = editText
        editTextDate.keyListener = null

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
                    context,
                    { _, selectedHour, selectedMinute ->
                        selectedDate.set(Calendar.HOUR_OF_DAY, selectedHour)
                        selectedDate.set(Calendar.MINUTE, selectedMinute)
                        val selectedDateTime = SimpleDateFormat(
                            "yyyy-MM-dd HH:mm",
                            Locale.getDefault()
                        ).format(selectedDate.time)
                        editTextDate.setText(selectedDateTime)
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.show()
            }
        val datePickerDialog = DatePickerDialog(
            context,
            dateTimeListener,
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}