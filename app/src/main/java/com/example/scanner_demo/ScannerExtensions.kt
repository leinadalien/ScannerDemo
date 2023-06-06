package com.example.scanner_demo

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.websitebeaver.documentscanner.DocumentScanner

fun DocumentScanner.initLauncher(activity: ComponentActivity) : ActivityResultLauncher<Intent> {
    return activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult -> handleDocumentScanIntentResult(result) }
}