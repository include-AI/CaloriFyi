package com.example.calorifyi

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorifyi.TensorFlowHelper.imageSize
import com.example.calorifyi.ui.theme.*


class PredictionActivity : ComponentActivity() {

//    lateinit var model: FruitModelV1Optimize
//    lateinit var imageView: ImageView
//    lateinit var button: Button
//    lateinit var bitmap: Bitmap
//
//    val labels = FileUtil.loadLabels(this, "labels.txt")
//    var imageProcessor = ImageProcessor.Builder().add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR)).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PredictView()
                }
            }

//            model = FruitModelV1Optimize.newInstance(this)



        }
    }

    private val imageSize = 224



//    override fun onDestroy() {
//        super.onDestroy()
//        model.close()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 101){
//            var uri = data?.data
//            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
//
//            getPredictions()
//        }
//    }
}
//

//

//
//    fun getPredictions(){
//
//        var image = TensorImage.fromBitmap(bitmap)
//        image = imageProcessor.process(image)




// Creates inputs for reference.
        //val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

// Runs model inference and gets result.
        //val outputs = model.process(inputFeature0)
        //val outputFeature0 = outputs.outputFeature0AsTensorBuffer

// Releases model resources if no longer used.

  //  }
//}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PredictView() {
    val cam3ctx = LocalContext.current
    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            photoUri = it
        }
    )
    
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(onb),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            photoUri?.let {
                if (Build.VERSION.SDK_INT < 28)
                    bitmap = MediaStore.Images.Media.getBitmap(cam3ctx.contentResolver, it)
                else {
                    val source = ImageDecoder.createSource(cam3ctx.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(
                        source,
                        ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->
                            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                        })
                }
            }

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Image from the gallery",
                    Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.padding(20.dp))

                val scaledBitmap = Bitmap.createScaledBitmap(it, imageSize, imageSize, false);

                TensorFlowHelper.classifyImage(scaledBitmap) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        var reception by remember(it) {
                            mutableStateOf(emptyList<Reception>())
                        }
                        LaunchedEffect(it) {
                            val newReception = recep(it)
                            reception = newReception
                        }

//                        Text(text = "Image is classified as:", fontFamily = googleSans)
                        Text(text = it, color = Color.Black, fontSize = 24.sp, fontFamily = googleSans)
                        reception.forEach { item ->
                            Text("\n\nPer ${item.quantity} gm \n\nCalories: ${item.calories}", fontFamily = googleSans, textAlign = TextAlign.Start)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.padding(25.dp))
            
            Button(onClick = { 
                galleryLauncher.launch("image/*")
            },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Purple200)
                ) {
                Text(
                    text = "Select an Image",
                    fontFamily = googleSans
                )
            }
            
        }
    }
}


//fun getPredictions(byteBuffer: ByteBuffer){
//    val model = FruitModelV1Optimize.newInstance()
//
//// Creates inputs for reference.
//    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//    inputFeature0.loadBuffer(byteBuffer)
//
//// Runs model inference and gets result.
//
//    val outputs = model.process(inputFeature0)
//    val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//// Releases model resources if no longer used.
//    model.close()
//
//}


//    val intent = Intent()
//    intent.type = "image/*"
//    intent.action = Intent.ACTION_GET_CONTENT
//
//
//    Column(){
//        Button(
//            onClick = { startActivityForResult() }) {
//            Text(text = "Select Images")
//        }
//    }
//}