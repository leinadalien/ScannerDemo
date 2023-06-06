package com.example.scanner_demo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.websitebeaver.documentscanner.DocumentScanner

class ScannerViewModel : ViewModel() {
    private var _scannerResults = ArrayList<String>()
    private lateinit var _launcher : ActivityResultLauncher<Intent>
    private lateinit var _scanner : DocumentScanner
    private var isFrontSide = true
    fun setScanner(activity: ComponentActivity) {

        _scanner = DocumentScanner(
            activity,
            { croppedImageResults -> onSuccessScanning(croppedImageResults)},
            {
                    error -> _errorMessage.postValue(error)
            },
            {
            _errorMessage.postValue("Canceled")
            },
            maxNumDocuments = 2
        )
        _launcher = _scanner.initLauncher(activity)
    }
    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    fun startScan(){
        _launcher.launch(_scanner.createDocumentScanIntent())
    }

    private var _cardImage = MutableLiveData<Bitmap>()
    val cardImage: LiveData<Bitmap> = _cardImage

    private fun onSuccessScanning(croppedImageResults: ArrayList<String>) {
        _scannerResults = croppedImageResults
        updateImage()
        _isFlippable.postValue(_scannerResults.size > 1)
    }
    private var _isFlippable = MutableLiveData<Boolean>()
    val isFlippable :LiveData<Boolean> = _isFlippable
    private fun updateImage() {
        _cardImage.postValue(
            BitmapFactory.decodeFile(_scannerResults[if (isFrontSide) 0 else 1])
        )
    }

    fun flipCard(){
        isFrontSide = !isFrontSide
        updateImage()
    }
}