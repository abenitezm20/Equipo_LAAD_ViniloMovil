package com.laad.viniloapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.laad.viniloapp.utilities.AppRole
import com.laad.viniloapp.utilities.ROLE
import com.laad.viniloapp.views.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val visitorButton: Button = findViewById(R.id.visitor_home_button)
        visitorButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            intent.putExtra(ROLE, AppRole.VISITOR.value)
            ViniloApp.rol = AppRole.VISITOR
            startActivity(intent)
        })

        val collectorButton: Button = findViewById(R.id.collector_home_button)
        collectorButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            intent.putExtra(ROLE, AppRole.COLLECTOR.value)
            ViniloApp.rol = AppRole.COLLECTOR
            startActivity(intent)
        })
    }

}