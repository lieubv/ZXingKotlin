package com.vmcplus.zxingkotlin

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.*
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Generating QR-Code
        val bitmap = generateAnyCode("9037495473", BarcodeFormat.QR_CODE)

        // Generating Barcode
        //val bitmap = generateAnyCode("9037495473", BarcodeFormat.CODE_128)

        imageView.setImageBitmap(bitmap)

    }

    private fun generateAnyCode(string: String, codeFormat: BarcodeFormat): Bitmap {
        val width = 400
        val height = 400
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter: Writer = MultiFormatWriter()
        try {
            val hintMap: Hashtable<EncodeHintType, ErrorCorrectionLevel> = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
            val byteMatrix: BitMatrix = codeWriter.encode(string, codeFormat, width, height, hintMap)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (byteMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
        return bitmap
    }
}