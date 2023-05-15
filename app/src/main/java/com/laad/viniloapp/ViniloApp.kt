package com.laad.viniloapp

import android.app.Application
import com.laad.viniloapp.data.database.VinylRoomDatabase

class ViniloApp : Application() {

    val database by lazy { VinylRoomDatabase.getDatabase(this) }

}