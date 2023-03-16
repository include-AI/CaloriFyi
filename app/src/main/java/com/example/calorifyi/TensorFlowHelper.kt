package com.example.calorifyi

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.calorifyi.ml.FruitModelV1Optimize
import com.example.calorifyi.ml.FruitModelV2Optimize
import com.example.calorifyi.ml.FruitModelV3Optimize
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

object TensorFlowHelper {
    val imageSize = 224

    @Composable
    fun classifyImage(image: Bitmap? = null, callback : (@Composable (fruit : String) -> Unit)){
        val model = FruitModelV3Optimize.newInstance(LocalContext.current)

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image?.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classes = arrayOf(
            "Apple",
            "Banana",
            "Carambola",
            "Guava",
            "Kiwi",
            "Mango",
            "Orange",
            "Peach",
            "Pear",
            "Persimmon",
            "Pitaya",
            "Plum",
            "Pomegranate",
            "Tomatoes",
            "Muskmelon"
        )
        callback.invoke(classes[maxPos])
// Releases model resources if no longer used.
        model.close()
    }
}