package com.vmcplus.zxingkotlin

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class InlineScanActivity : AppCompatActivity() {
    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false
    lateinit var barcodeView: DecoratedBarcodeView
    lateinit var btnScan: Button
    lateinit var btnTorch: Button
    lateinit var txtResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Scan Once"
        // init views
        barcodeView = this.findViewById(R.id.barcodeView)
        btnScan = this.findViewById(R.id.btnScan)
        btnTorch = this.findViewById(R.id.btnTorch)
        txtResult = this.findViewById(R.id.txtResult)
        //
        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        btnScan.setOnClickListener {
            txtResult.text = "scaning..."
            barcodeView.decodeSingle(object: BarcodeCallback {
                override fun barcodeResult(result: BarcodeResult?) {
                    result?.let {
                        txtResult.text = it.text

                        val vib: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                        if(vib.hasVibrator()){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                // void vibrate (VibrationEffect vibe)
                                vib.vibrate(
                                    VibrationEffect.createOneShot(
                                        100,
                                        // The default vibration strength of the device.
                                        VibrationEffect.DEFAULT_AMPLITUDE
                                    )
                                )
                            }else{
                                // This method was deprecated in API level 26
                                vib.vibrate(100)
                            }
                        }
                    }
                }

                override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                }
            })
        }

        btnTorch.setOnClickListener {
            if(torchState){
                torchState = false
                barcodeView.setTorchOff()
            } else {
                torchState = true
                barcodeView.setTorchOn()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }
}
