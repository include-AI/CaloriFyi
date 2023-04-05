package com.example.calorifyi

import android.graphics.*
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calorifyi.ui.theme.googleSans
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import org.w3c.dom.Text

object ObjectDetectionHelper{

    @Composable
    fun ObjectDetect(bitmap: Bitmap, callback : (@Composable (fruit : String) -> Unit)){
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.3f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            LocalContext.current,
            "efdlite3_v0.3.tflite",
            options
        )



        // Step 3: Feed given image to the detector
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text)
        }
        // Draw the detection result on the bitmap and show it.
        val imageBitmap = bitmap.asImageBitmap()
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        val modifier = Modifier
            .wrapContentSize()
            .aspectRatio(imageBitmap.width.toFloat() / imageBitmap.height)
        Image(bitmap = imgWithResult.asImageBitmap(), contentDescription = null, modifier = modifier)

//        DebugPrint(results = results)
        Spacer(modifier = Modifier.height(10.dp))


        var outputs: List<String>

        for (obj in results) {
            for (category in obj.categories) {
                val confidence: Int = category.score.times(100).toInt()
                val labels = setOf(category.label.toString())


                if (confidence>50){
                    outputs = labels.toList()
                    for (z in outputs){
                        callback.invoke(z)
                    }
                }
            }
        }



//        return results.count()
    }
    
    @Composable
    fun DebugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox


            Text("Detected object: $i ")
            Text("  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Text("    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Text("    Confidence: ${confidence}%")
            }
        }
    }



    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.BLUE
            pen.strokeWidth = 4F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.GREEN
            pen.strokeWidth = 2F

            pen.textSize = 80F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

    data class DetectionResult(val boundingBox: RectF, val text: String)


}