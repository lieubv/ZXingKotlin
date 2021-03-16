# ZXing Android Sample App
This is a Sample app to generate QR-Code using ZXing library in Kotlin.
## Screenshots
![screen](../master/screenshots/screen.png)

## Abstract
```kotlin
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
```

## Tech

Used following 3rd party libraries 

* [ZXing](https://github.com/zxing/zxing) - ZXing is an open-source, multi-format 1D/2D barcode image processing library implemented in Java, with ports to other languages


License
----

MIT
