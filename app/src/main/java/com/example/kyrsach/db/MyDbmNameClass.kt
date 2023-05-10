package com.example.kyrsach.db

import android.provider.BaseColumns

object MyDbmNameClass: BaseColumns {
    const val TABLE_NAME = "users_table"
    const val COLUMN_ID_USES = "id"
    const val COLUMN_NAME_FIO = "fio"
    const val COLUMN_NAME_DATE_START = "date_start"
    const val COLUMN_NAME_DATE_FINISH = "date_finish"
    const val COLUMN_NAME_NUMBER = "number"
    const val COLUMN_NAME_PRODUCT = "product"
    const val COLUMN_NAME_COUNT = "count"
    const val COLUMN_NAME_MATERIAL = "material"

    const val DATABASE_VERSION = 7
    const val DATABASE_NAME = "Users.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_ID_USES INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_FIO TEXT, " +
            "$COLUMN_NAME_DATE_START TEXT, " +
            "$COLUMN_NAME_DATE_FINISH TEXT, " +
            "$COLUMN_NAME_NUMBER TEXT, " +
            "$COLUMN_NAME_PRODUCT TEXT, " +
            "$COLUMN_NAME_COUNT INTEGER, " +
            "$COLUMN_NAME_MATERIAL TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    //--------------------------------------------------------------------------------------
    //таблица для цен фанеры
    const val TABLE_NAME_PRICE = "price_table"
    const val COLUMN_ID_PRICE = "id_price"
    const val COLUMN_NAME_PRICE_MATERIAL = "price_material"
    const val COLUMN_NAME_PRICE = "price"

    const val CREATE_TABLE_PRICE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME_PRICE (" +
            "$COLUMN_ID_PRICE INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_PRICE_MATERIAL TEXT, " +
            "$COLUMN_NAME_PRICE TEXT )"

    const val ADD_PRICE = "INSERT INTO price_table ($COLUMN_NAME_PRICE_MATERIAL, $COLUMN_NAME_PRICE)" +
            " values " +
            "(\"3мм, 3/4\", \"280\")," +
            "(\"3мм, 4/4\", \"400\")," +
            "(\"4мм, 3/4\", \"335\")," +
            "(\"4мм, 4/4\", \"450\")"

    const val SQL_DELETE_TABLE_PRICE = "DROP TABLE IF EXISTS $TABLE_NAME_PRICE"
}