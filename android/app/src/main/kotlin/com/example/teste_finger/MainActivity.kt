package com.example.teste_finger

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Base64
import androidx.annotation.NonNull
import asia.kanopi.fingerscan.Fingerprint
import asia.kanopi.fingerscan.Status
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity: FlutterActivity() {

    private val fingerprint: Fingerprint? = Fingerprint();
    val image: ByteArray? = null
    var imageBity:  ByteArray? = null

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        MethodChannel(flutterEngine.dartExecutor,"MyChanel").setMethodCallHandler{
            call,result ->
            if (call.method=="myNativeFunction"){
                if (fingerprint != null) {
                    imageBity = null;
                    fingerprint.scan(this, printHandler, updateHandler)
                    result.success("Success");
                }
            }else if (call.method=="getPhotoArray"){
                result.success(imageBity);
            }
            
        }
    }

    var printHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {

            val image: ByteArray
            var errorMessage = "empty"
            val status: Int = msg.getData().getInt("status")
//            val intent = Intent()
//            intent.putExtra("status", status)
            if (status == Status.SUCCESS) {

                image = msg.getData().getByteArray("img")
                imageBity = image
                print(image)
            } else {
                errorMessage = msg.getData().getString("errorMessage")

            }

//            finish()
        }
    }

    var updateHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val status = msg.data.getInt("status")
        }
    }

}
