//package com.example.calorificator
//
//import android.net.Uri
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberImagePainter
//import com.example.calorificator.ui.theme.CalorificatorTheme
//
//
//
//class PredictionActivity : ComponentActivity() {
//
////    lateinit var model: FruitModelV1Optimize
////    lateinit var imageView: ImageView
////    lateinit var button: Button
////    lateinit var bitmap: Bitmap
////
////    val labels = FileUtil.loadLabels(this, "labels.txt")
////    var imageProcessor = ImageProcessor.Builder().add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR)).build()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            CalorificatorTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    PredictView()
//                }
//            }
//
//            //model = FruitModelV1Optimize.newInstance(this)
//
//
//            //button.setOnClickListener{
//
//        }
//    }
//}
////
////    override fun onDestroy() {
////        super.onDestroy()
////        model.close()
////    }
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
////        super.onActivityResult(requestCode, resultCode, data)
////        if(requestCode == 101){
////            var uri = data?.data
////            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
////
////            getPredictions()
////        }
////    }
////
////    fun getPredictions(){
////
////        var image = TensorImage.fromBitmap(bitmap)
////        image = imageProcessor.process(image)
//
//
//
//
//// Creates inputs for reference.
//        //val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//
//// Runs model inference and gets result.
//        //val outputs = model.process(inputFeature0)
//        //val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//// Releases model resources if no longer used.
//
//  //  }
////}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun PredictView() {
//
//    var selectImages by remember { mutableStateOf(listOf<Uri>()) }

//    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
//        selectImages = it
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp),
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            modifier = Modifier.padding(10.dp),
//            onClick = { galleryLauncher.launch("image/*") }) {
//            Text(text = "Select Images")
//        }
//
//        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
//            items(selectImages) { uri ->
//                Image(
//                    painter = rememberImagePainter(uri),
//                    contentScale = ContentScale.FillWidth,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(16.dp, 8.dp)
//                        .size(100.dp)
//                        .clickable {  }
//                    )
//
//            }
//        }
//    }
//}
//
//
////    val intent = Intent()
////    intent.type = "image/*"
////    intent.action = Intent.ACTION_GET_CONTENT
////
////
////    Column(){
////        Button(
////            onClick = { startActivityForResult() }) {
////            Text(text = "Select Images")
////        }
////    }
////}
//
//
//
