package com.laad.viniloapp

import android.app.Application
import com.laad.viniloapp.data.database.VinylRoomDatabase
import com.laad.viniloapp.utilities.AppRole

class ViniloApp : Application() {

    companion object {
        var rol: AppRole? = null
    }

    val database by lazy { VinylRoomDatabase.getDatabase(this) }

}