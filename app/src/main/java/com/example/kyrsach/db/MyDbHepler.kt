package com.example.kyrsach.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHepler(context: Context):
    SQLiteOpenHelper(context, MyDbmNameClass.DATABASE_NAME, null, MyDbmNameClass.DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDbmNameClass.CREATE_TABLE)
        db?.execSQL(MyDbmNameClass.CREATE_TABLE_PRICE)
        db?.execSQL(MyDbmNameClass.ADD_PRICE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(MyDbmNameClass.SQL_DELETE_TABLE)
        db?.execSQL(MyDbmNameClass.SQL_DELETE_TABLE_PRICE)
        onCreate(db)
    }
}