package com.vmcplus.zxingkotlin

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

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
            val text = editText.text.toString()
            if (text.isNotBlank()) {
                val bitmap = generateQRCode(text)
                imageView.setImageBitmap(bitmap)
            }
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
}