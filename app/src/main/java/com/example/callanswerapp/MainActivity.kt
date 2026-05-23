package com.example.callanswerapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 100, 50, 50)
        }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 0, 0, 60) }

        val btnPermissions = Button(this).apply {
            text = "1. طلب صلاحيات الهاتف"
            textSize = 20f
            setPadding(20, 40, 20, 40)
            this.layoutParams = layoutParams
            setOnClickListener { requestPhonePermissions() }
        }

        val btnAccessibility = Button(this).apply {
            text = "2. تفعيل خدمة إمكانية الوصول"
            textSize = 20f
            setPadding(20, 40, 20, 40)
            this.layoutParams = layoutParams
            setOnClickListener {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "ابحث عن التطبيق وقم بتفعيله", Toast.LENGTH_LONG).show()
            }
        }

        layout.addView(btnPermissions)
        layout.addView(btnAccessibility)
        setContentView(layout)
    }

    private fun requestPhonePermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ANSWER_PHONE_CALLS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            Toast.makeText(this, "الصلاحيات ممنوحة مسبقاً!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "تم منح الصلاحيات بنجاح", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "يرجى منح الصلاحيات لكي يعمل التطبيق", Toast.LENGTH_LONG).show()
            }
        }
    }
}
