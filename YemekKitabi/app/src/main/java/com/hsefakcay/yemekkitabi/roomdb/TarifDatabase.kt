package com.hsefakcay.yemekkitabi.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hsefakcay.yemekkitabi.model.Tarif

@Database(entities = [Tarif::class], version = 1)
abstract class TarifDatabase: RoomDatabase(){
    abstract fun  TarifDAO(): TarifDAO
}