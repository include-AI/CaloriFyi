package com.example.calorifyi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.example.calorifyi.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FlipCameraAndroid
import androidx.compose.material.icons.sharp.PhotoLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KFunction1


@Composable
fun CameraOpen(dir: File, onImageCaptured: KFunction1<Uri, Unit>){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    SimpleCameraPreview(
        modifier = Modifier.fillMaxSize(),
        context = context,
        lifecycleOwner = lifecycleOwner,
        outputDirectory = dir,
        onMediaCaptured = onImageCaptured
    )
}

@Composable
fun SimpleCameraPreview(
    modifier: Modifier = Modifier,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    outputDirectory: File,
    onMediaCaptured: KFunction1<Uri, Unit>
    ){
    val navController = rememberNavController()
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    val camera: Camera? = null
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    var flashEnabled by remember { mutableStateOf(false) }
    var flashRes by remember { mutableStateOf(R.drawable.flashlight_on_48px)}
    val executor = ContextCompat.getMainExecutor(context)
    var cameraSelector: CameraSelector?
    val cameraProvider = cameraProviderFuture.get()

    Box {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                cameraProviderFuture.addListener({
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .apply {
                            setAnalyzer(executor, FaceAnalyzer())
                        }
                    imageCapture = ImageCapture.Builder()
                        .setTargetRotation(previewView.display.rotation)
                        .build()
                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageCapture,
                        preview
                    )
                }, executor)
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                previewView
            }
        )
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.TopCenter)
        ) {
//            IconButton(
//                onClick = {
//
//                },
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.ArrowBack,
//                    contentDescription = "back arrow",
//                    tint = MaterialTheme.colors.surface
//                )
//            }

        }

        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Transparent, RoundedCornerShape(15.dp))
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        ) {
//            IconButton(
//                onClick = {
//                    camera?.let {
//                        if (it.cameraInfo.hasFlashUnit()) {
//                            flashEnabled = !flashEnabled
//                            flashRes = if (flashEnabled) R.drawable.flashlight_off_48px else R.drawable.flashlight_on_48px
//                            it.cameraControl.enableTorch(flashEnabled)
//                        }
//                    }
//                }
//            ) {
//                Icon(
//                    painter = painterResource(id = flashRes),
//                    contentDescription = null,
//                    modifier = Modifier.size(35.dp),
//                    tint = MaterialTheme.colors.surface
//                )
//            }


            IconButton(
                onClick = {
                    lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                    cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector as CameraSelector,
                        imageCapture,
                        preview
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Sharp.FlipCameraAndroid,
                    contentDescription = "Flip",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colors.surface)
            }

            
            Button(
                onClick = {
                    val imgCapture = imageCapture ?: return@Button
                    val photoFile = File(
                        outputDirectory,
                        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                            .format(System.currentTimeMillis()) + ".jpg"
                    )
                    val cameraIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                    imgCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                onMediaCaptured(savedUri)

                            }

                            override fun onError(exception: ImageCaptureException) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                            


                        }
                    )
                },
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(7.dp, Color.White, CircleShape),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            ) {
                
            }


            IconButton(
                onClick = {
                    context.startActivity(Intent(context, ImageView::class.java))
                },
            ){
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Sharp.PhotoLibrary,
                    contentDescription = "Photo Library",
                    tint = MaterialTheme.colors.surface
                )
            }
        }
    }
    
}

private class FaceAnalyzer(): ImageAnalysis.Analyzer{
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val imagePic = image.image
        imagePic?.close()
    }
}






