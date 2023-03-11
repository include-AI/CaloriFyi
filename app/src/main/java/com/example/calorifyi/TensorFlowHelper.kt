package com.example.calorifyi

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.calorifyi.ml.FruitModelV1Optimize
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

object TensorFlowHelper {
    val imageSize = 224

    @Composable
    fun classifyImage(image: Bitmap? = null, callback : (@Composable (fruit : String) -> Unit)){
        val model = FruitModelV1Optimize.newInstance(LocalContext.current)

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
            "Apple Braeburn",
            "Apple Crimson Snow",
            "Apple Golden 1",
            "Apple Golden 2",
            "Apple Golden 3",
            "Apple Granny Smith",
            "Apple Pink Lady",
            "Apple Red 1",
            "Apple Red 2",
            "Apple Red 3",
            "Apple Red Delicious",
            "Apple Red Yellow 1",
            "Apple Red Yellow 2",
            "Apricot",
            "Avocado",
            "Avocado ripe",
            "Banana",
            "Banana Lady Finger",
            "Banana Red",
            "Beetroot",
            "Blueberry",
            "Cactus fruit",
            "Cantaloupe 1",
            "Cantaloupe 2",
            "Carambula",
            "Cauliflower",
            "Cherry 1",
            "Cherry 2",
            "Cherry Rainier",
            "Cherry Wax Black",
            "Cherry Wax Red",
            "Cherry Wax Yellow",
            "Chestnut",
            "Clementine",
            "Cocos",
            "Corn",
            "Corn Husk",
            "Cucumber Ripe",
            "Cucumber Ripe 2",
            "Dates",
            "Eggplant",
            "Fig",
            "Ginger Root",
            "Granadilla",
            "Grape Blue",
            "Grape Pink",
            "Grape White",
            "Grape White 2",
            "Grape White 3",
            "Grape White 4",
            "Grapefruit Pink",
            "Grapefruit White",
            "Guava",
            "Hazelnut",
            "Huckleberry",
            "Kaki",
            "Kiwi",
            "Kohlrabi",
            "Kumquats",
            "Lemon",
            "Lemon Meyer",
            "Limes",
            "Lychee",
            "Mandarine",
            "Mango",
            "Mango Red",
            "Mangostan",
            "Maracuja",
            "Melon Piel de Sapo",
            "Mulberry",
            "Nectarine",
            "Nectarine Flat",
            "Nut Forest",
            "Nut Pecan",
            "Onion Red",
            "Onion Red Peeled",
            "Onion White",
            "Orange",
            "Papaya",
            "Passion Fruit",
            "Peach",
            "Peach 2",
            "Peach Flat",
            "Pear",
            "Pear 2",
            "Pear Abate",
            "Pear Forelle",
            "Pear Kaiser",
            "Pear Monster",
            "Pear Red",
            "Pear Stone",
            "Pear Williams",
            "Pepino",
            "Pepper Green",
            "Pepper Orange",
            "Pepper Red",
            "Pepper Yellow",
            "Physalis",
            "Physalis with Husk",
            "Pineapple",
            "Pineapple Mini",
            "Pitahaya Red",
            "Plum",
            "Plum 2",
            "Plum 3",
            "Pomegranate",
            "Pomelo Sweetie",
            "Potato Red",
            "Potato Red Washed",
            "Potato Sweet",
            "Potato White",
            "Quince",
            "Rambutan",
            "Raspberry",
            "Redcurrant",
            "Salak",
            "Strawberry",
            "Strawberry Wedge",
            "Tamarillo",
            "Tangelo",
            "Tomato 1",
            "Tomato 2",
            "Tomato 3",
            "Tomato 4",
            "Tomato Cherry Red",
            "Tomato Heart",
            "Tomato Maroon",
            "Tomato Yellow",
            "Tomato not Ripened",
            "Walnut",
            "Watermelon"
        )
        callback.invoke(classes[maxPos])
// Releases model resources if no longer used.
        model.close()
    }
}