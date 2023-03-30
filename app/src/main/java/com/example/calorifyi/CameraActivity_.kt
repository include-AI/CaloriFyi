package com.example.calorifyi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import com.example.calorifyi.ui.theme.Purple200
import com.example.calorifyi.ui.theme.googleSans
import com.example.calorifyi.ui.theme.onb
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {
    private lateinit var context : Context
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if (isGranted){
            Log.i("camera", "Permission Granted")
            shouldShowCamera.value = true
        } else {
            Log.i("camera", "Permission Denied")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (shouldShowCamera.value) {
                        CameraOpen(dir = getOutputDirectory(), onImageCaptured = ::handleImageCaptured)
                    }

                    if (shouldShowPhoto.value) {
                        var imgBitmap: Bitmap? = null
                        //val file: File = File(Environment.getExternalStorageDirectory(), "read.me")
                        val uri = photoUri
                        imgBitmap = BitmapFactory.decodeFile(uri.encodedPath)
                        val scaledBitmap = imgBitmap?.let { Bitmap.createScaledBitmap(it,
                            TensorFlowHelper.imageSize,
                            TensorFlowHelper.imageSize, false) }

                        TensorFlowHelper.classifyImage(scaledBitmap) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(onb),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Image(
                                    painter = rememberImagePainter(photoUri),
                                    contentDescription = null,
                                    modifier = Modifier.wrapContentHeight()
                                )
                                Spacer(modifier = Modifier.height(20.dp))
//                                Text(text = "Image is classified as:")
                                Text(text = it, color = Color.Black, fontSize = 24.sp, fontFamily = googleSans)
                                val modelOutput = it
                                Spacer(modifier = Modifier.height(5.dp))
                                context = LocalContext.current
                                Row(
                                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                                ){
                                    Button(
                                        onClick = {
                                            val i = Intent(context, ReceptionActivity::class.java)
                                            i.putExtra("query_image", modelOutput)
                                            i.data = photoUri
                                            context.startActivity(i)
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(Purple200),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = "Process", fontFamily = googleSans)
                                    }
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Button(
                                        onClick = {},
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(Purple200),
                                        modifier = Modifier.weight(1f)
                                    ){
                                        Text(text = "Add to Diet", fontFamily = googleSans)
                                    }

                                }



                            }
                        }

                    }
                }
            }
        }
        requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    private fun requestCameraPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("camera", "Permission previously Granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("camera", "Show Camera Permission dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCaptured(uri: Uri) {
        Log.i("camera", "Image Captured: $uri")
        shouldShowCamera.value = false

        photoUri = uri
        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}






