package com.example.callanswerapp

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.telecom.TelecomManager
import android.view.KeyEvent
import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class CallAnswerService : AccessibilityService() {

    override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            
            val hasAnswerPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED
            val hasStatePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

            if (hasAnswerPerm && hasStatePerm && telecomManager.isRinging) {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    try {
                        telecomManager.acceptRingingCall()
                        Toast.makeText(this, "تم الرد على المكالمة!", Toast.LENGTH_SHORT).show()
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }
                return true 
            }
        }
        return super.onKeyEvent(event)
    }
}
