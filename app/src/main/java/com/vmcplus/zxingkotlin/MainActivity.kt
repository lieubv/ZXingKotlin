package com.vmcplus.zxingkotlin

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Generating Barcode
        val bitmap = generateBarCode("9037495473")

        // Generating QR Code
        //val bitmap = generateQRCode("9037495473")

        imageView.setImageBitmap(bitmap)

    }

    private fun generateQRCode(string: String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val codeWriter: Writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = codeWriter.encode(string, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
        return bitmap
    }

    private fun generateBarCode(string: String): Bitmap {
        val width = 400
        val height = 200
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter: Writer = Code128Writer()
        try {
            val hintMap: Hashtable<EncodeHintType, ErrorCorrectionLevel> = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
            val byteMatrix: BitMatrix = codeWriter.encode(string, BarcodeFormat.CODE_128, width, height, hintMap)
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