package com.vmcplus.zxingkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator

/**
 * Authored by Riyas Valiyadan
 * Contact me @ https://www.linkedin.com/in/riyasvmc
 */

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing views
        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)
        imageView = findViewById(R.id.imageView)

        button.setOnClickListener {
//            val text = editText.text.toString()
//            if (text.isNotBlank()) {
//                val bitmap = generateQRCode(text)
//                imageView.setImageBitmap(bitmap)
//            }
            scanQRCode()
        }
    }

    private fun generateQRCode(text: String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) { Log.d(TAG, "generateQRCode: ${e.message}") }
        return bitmap
    }

    private fun scanQRCode(){
        kotlin.run {
            IntentIntegrator(this).setOrientationLocked(false).initiateScan()
        }
//        val integrator = IntentIntegrator(this).apply {
//            captureActivity = CaptureActivity::class.java
//            setOrientationLocked(false)
//            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
//            setPrompt("Scanning Code")
//        }
//        integrator.initiateScan()
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}