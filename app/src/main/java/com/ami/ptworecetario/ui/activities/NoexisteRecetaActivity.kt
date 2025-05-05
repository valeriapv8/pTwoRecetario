package com.ami.ptworecetario.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.ami.ptworecetario.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NoexisteRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_noexiste_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.noexiste)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnAgregarReceta).setOnClickListener {
            val intent = Intent(this, AddRecetaActivity::class.java)
            startActivity(intent)
        }
    }
}