package com.example.kyrsach.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHepler(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(use: Use){
        val values = ContentValues().apply {
            put(MyDbmNameClass.COLUMN_NAME_FIO, use.FIO)
            put(MyDbmNameClass.COLUMN_NAME_DATE_START, use.DATE_START)
            put(MyDbmNameClass.COLUMN_NAME_DATE_FINISH, use.DATE_FINISH)
            put(MyDbmNameClass.COLUMN_NAME_NUMBER, use.NUMBER)
            put(MyDbmNameClass.COLUMN_NAME_PRODUCT, use.PRODUCT)
            put(MyDbmNameClass.COLUMN_NAME_COUNT, use.COUNT)
            put(MyDbmNameClass.COLUMN_NAME_MATERIAL, use.MATERIAL)
        }
        db?.insert(MyDbmNameClass.TABLE_NAME, null, values)
    }

    fun readDbData(): MutableList<Use>{
        val dataList = mutableListOf<Use>()
        val cursor = db?.query(MyDbmNameClass.TABLE_NAME, null, null, null, null, null, null)
        while (cursor?.moveToNext()!!){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_ID_USES))
            val dataFIO = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_FIO))
            val dataDateStart = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_DATE_START))
            val dataDateFinish = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_DATE_FINISH))
            val dataNumber = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_NUMBER))
            val dataProduct = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_PRODUCT))
            val dataCount = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_COUNT))
            val dataMaterial = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_MATERIAL))

            val use = Use(id, dataFIO, dataDateStart, dataDateFinish, dataNumber, dataProduct, dataCount, dataMaterial)
            dataList.add(use)
        }
        cursor.close()
        return dataList
    }

    fun deleteUses(id:Int){
        db?.delete(MyDbmNameClass.TABLE_NAME, "${MyDbmNameClass.COLUMN_ID_USES}=?", arrayOf((id + 1).toString()))
    }

    fun insertToDbPrice(price: Price){
        val values = ContentValues().apply {
            put(MyDbmNameClass.COLUMN_NAME_PRICE_MATERIAL, price.PRICE_MATERIAL)
            put(MyDbmNameClass.COLUMN_NAME_PRICE, price.PRICE)
        }
        db?.insert(MyDbmNameClass.TABLE_NAME, null, values)
    }

    fun readDbDataPrice(): MutableList<Price>{
        val dataList = mutableListOf<Price>()
        val cursor = db?.query(MyDbmNameClass.TABLE_NAME_PRICE, null, null, null, null, null, null)
        while (cursor?.moveToNext()!!){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_ID_PRICE))
            val material = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_PRICE_MATERIAL))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(MyDbmNameClass.COLUMN_NAME_PRICE))
            val priceList = Price(id, material, price)
            dataList.add(priceList)
        }
        cursor.close()
        return dataList
    }

    fun updatePrice(price: Price){
        val values = ContentValues()
        values.put(MyDbmNameClass.COLUMN_NAME_PRICE, price.PRICE)
        val result = db?.update(MyDbmNameClass.TABLE_NAME_PRICE, values, "${MyDbmNameClass.COLUMN_ID_PRICE}=?", arrayOf((price.ID).toString()))
        println("result=$result")
    }

    fun allPrice(): Int{
        var result = 0
        val cursor = db?.rawQuery("SELECT SUM(${MyDbmNameClass.COLUMN_NAME_COUNT}) FROM ${MyDbmNameClass.TABLE_NAME}", null)
        if (cursor?.moveToFirst()!!) {
            result = cursor.getInt(0)
        }
        cursor.close()
        return result
    }

    fun getMaterialPrice(material: String): Int{
        var result = 0
        val cursor = db?.rawQuery("SELECT SUM(${MyDbmNameClass.COLUMN_NAME_COUNT}) FROM ${MyDbmNameClass.TABLE_NAME} WHERE  ${MyDbmNameClass.COLUMN_NAME_MATERIAL} = '${material}'", null)
        if (cursor?.moveToFirst()!!) {
            result = cursor.getInt(0)
        }
        cursor.close()
        return result
    }

    fun updateUses(use: Use){
        val values = ContentValues()
        values.put(MyDbmNameClass.COLUMN_NAME_FIO, use.FIO)
        values.put(MyDbmNameClass.COLUMN_NAME_DATE_START, use.DATE_START)
        values.put(MyDbmNameClass.COLUMN_NAME_DATE_FINISH, use.DATE_FINISH)
        values.put(MyDbmNameClass.COLUMN_NAME_NUMBER, use.NUMBER)
        values.put(MyDbmNameClass.COLUMN_NAME_PRODUCT, use.PRODUCT)
        values.put(MyDbmNameClass.COLUMN_NAME_COUNT, use.COUNT)
        values.put(MyDbmNameClass.COLUMN_NAME_MATERIAL, use.MATERIAL)
        val result = db?.update(MyDbmNameClass.TABLE_NAME, values, "${MyDbmNameClass.COLUMN_ID_USES}=?", arrayOf((use.ID + 1).toString()))
        println("result=$result")
    }

    fun getTotalUsageTime(): Double {
        var totalUsageTime: Double = 0.0

        val query = "SELECT ${MyDbmNameClass.COLUMN_NAME_DATE_START}, ${MyDbmNameClass.COLUMN_NAME_DATE_FINISH} FROM ${MyDbmNameClass.TABLE_NAME}"
        val cursor = db?.rawQuery(query, null)

        while (cursor?.moveToNext()!!) {
            val startTimeString = cursor.getString(0)
            val endTimeString = cursor.getString(1)

            val startTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(startTimeString)
            val endTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(endTimeString)

            val usageTime = endTime.time - startTime.time
            totalUsageTime += usageTime
        }
        cursor.close()

        return totalUsageTime / 60000
    }

    fun closeDb(){
        myDbHelper.close()
    }
}